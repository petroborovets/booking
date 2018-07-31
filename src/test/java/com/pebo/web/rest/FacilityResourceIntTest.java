package com.pebo.web.rest;

import com.pebo.BookingApp;

import com.pebo.domain.Facility;
import com.pebo.repository.FacilityRepository;
import com.pebo.service.FacilityService;
import com.pebo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.pebo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FacilityResource REST controller.
 *
 * @see FacilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class FacilityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FacilityRepository facilityRepository;

    

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacilityMockMvc;

    private Facility facility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacilityResource facilityResource = new FacilityResource(facilityService);
        this.restFacilityMockMvc = MockMvcBuilders.standaloneSetup(facilityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facility createEntity(EntityManager em) {
        Facility facility = new Facility()
            .name(DEFAULT_NAME);
        return facility;
    }

    @Before
    public void initTest() {
        facility = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacility() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // Create the Facility
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isCreated());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate + 1);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // Create the Facility with an existing ID
        facility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityRepository.findAll().size();
        // set the field null
        facility.setName(null);

        // Create the Facility, which fails.

        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isBadRequest());

        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilities() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList
        restFacilityMockMvc.perform(get("/api/facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", facility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facility.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFacility() throws Exception {
        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacility() throws Exception {
        // Initialize the database
        facilityService.save(facility);

        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility
        Facility updatedFacility = facilityRepository.findById(facility.getId()).get();
        // Disconnect from session so that the updates on updatedFacility are not directly saved in db
        em.detach(updatedFacility);
        updatedFacility
            .name(UPDATED_NAME);

        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacility)))
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Create the Facility

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacility() throws Exception {
        // Initialize the database
        facilityService.save(facility);

        int databaseSizeBeforeDelete = facilityRepository.findAll().size();

        // Get the facility
        restFacilityMockMvc.perform(delete("/api/facilities/{id}", facility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facility.class);
        Facility facility1 = new Facility();
        facility1.setId(1L);
        Facility facility2 = new Facility();
        facility2.setId(facility1.getId());
        assertThat(facility1).isEqualTo(facility2);
        facility2.setId(2L);
        assertThat(facility1).isNotEqualTo(facility2);
        facility1.setId(null);
        assertThat(facility1).isNotEqualTo(facility2);
    }
}
