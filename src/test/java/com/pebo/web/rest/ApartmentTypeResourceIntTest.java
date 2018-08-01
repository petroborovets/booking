package com.pebo.web.rest;

import com.pebo.BookingApp;

import com.pebo.domain.ApartmentType;
import com.pebo.repository.ApartmentTypeRepository;
import com.pebo.service.ApartmentTypeService;
import com.pebo.service.dto.ApartmentTypeDTO;
import com.pebo.service.mapper.ApartmentTypeMapper;
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
 * Test class for the ApartmentTypeResource REST controller.
 *
 * @see ApartmentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class ApartmentTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ApartmentTypeRepository apartmentTypeRepository;


    @Autowired
    private ApartmentTypeMapper apartmentTypeMapper;
    

    @Autowired
    private ApartmentTypeService apartmentTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApartmentTypeMockMvc;

    private ApartmentType apartmentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApartmentTypeResource apartmentTypeResource = new ApartmentTypeResource(apartmentTypeService);
        this.restApartmentTypeMockMvc = MockMvcBuilders.standaloneSetup(apartmentTypeResource)
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
    public static ApartmentType createEntity(EntityManager em) {
        ApartmentType apartmentType = new ApartmentType()
            .name(DEFAULT_NAME);
        return apartmentType;
    }

    @Before
    public void initTest() {
        apartmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createApartmentType() throws Exception {
        int databaseSizeBeforeCreate = apartmentTypeRepository.findAll().size();

        // Create the ApartmentType
        ApartmentTypeDTO apartmentTypeDTO = apartmentTypeMapper.toDto(apartmentType);
        restApartmentTypeMockMvc.perform(post("/api/apartment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartmentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ApartmentType in the database
        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ApartmentType testApartmentType = apartmentTypeList.get(apartmentTypeList.size() - 1);
        assertThat(testApartmentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createApartmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apartmentTypeRepository.findAll().size();

        // Create the ApartmentType with an existing ID
        apartmentType.setId(1L);
        ApartmentTypeDTO apartmentTypeDTO = apartmentTypeMapper.toDto(apartmentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApartmentTypeMockMvc.perform(post("/api/apartment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApartmentType in the database
        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apartmentTypeRepository.findAll().size();
        // set the field null
        apartmentType.setName(null);

        // Create the ApartmentType, which fails.
        ApartmentTypeDTO apartmentTypeDTO = apartmentTypeMapper.toDto(apartmentType);

        restApartmentTypeMockMvc.perform(post("/api/apartment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartmentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApartmentTypes() throws Exception {
        // Initialize the database
        apartmentTypeRepository.saveAndFlush(apartmentType);

        // Get all the apartmentTypeList
        restApartmentTypeMockMvc.perform(get("/api/apartment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getApartmentType() throws Exception {
        // Initialize the database
        apartmentTypeRepository.saveAndFlush(apartmentType);

        // Get the apartmentType
        restApartmentTypeMockMvc.perform(get("/api/apartment-types/{id}", apartmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apartmentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApartmentType() throws Exception {
        // Get the apartmentType
        restApartmentTypeMockMvc.perform(get("/api/apartment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApartmentType() throws Exception {
        // Initialize the database
        apartmentTypeRepository.saveAndFlush(apartmentType);

        int databaseSizeBeforeUpdate = apartmentTypeRepository.findAll().size();

        // Update the apartmentType
        ApartmentType updatedApartmentType = apartmentTypeRepository.findById(apartmentType.getId()).get();
        // Disconnect from session so that the updates on updatedApartmentType are not directly saved in db
        em.detach(updatedApartmentType);
        updatedApartmentType
            .name(UPDATED_NAME);
        ApartmentTypeDTO apartmentTypeDTO = apartmentTypeMapper.toDto(updatedApartmentType);

        restApartmentTypeMockMvc.perform(put("/api/apartment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartmentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ApartmentType in the database
        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeUpdate);
        ApartmentType testApartmentType = apartmentTypeList.get(apartmentTypeList.size() - 1);
        assertThat(testApartmentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingApartmentType() throws Exception {
        int databaseSizeBeforeUpdate = apartmentTypeRepository.findAll().size();

        // Create the ApartmentType
        ApartmentTypeDTO apartmentTypeDTO = apartmentTypeMapper.toDto(apartmentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApartmentTypeMockMvc.perform(put("/api/apartment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApartmentType in the database
        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApartmentType() throws Exception {
        // Initialize the database
        apartmentTypeRepository.saveAndFlush(apartmentType);

        int databaseSizeBeforeDelete = apartmentTypeRepository.findAll().size();

        // Get the apartmentType
        restApartmentTypeMockMvc.perform(delete("/api/apartment-types/{id}", apartmentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApartmentType> apartmentTypeList = apartmentTypeRepository.findAll();
        assertThat(apartmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApartmentType.class);
        ApartmentType apartmentType1 = new ApartmentType();
        apartmentType1.setId(1L);
        ApartmentType apartmentType2 = new ApartmentType();
        apartmentType2.setId(apartmentType1.getId());
        assertThat(apartmentType1).isEqualTo(apartmentType2);
        apartmentType2.setId(2L);
        assertThat(apartmentType1).isNotEqualTo(apartmentType2);
        apartmentType1.setId(null);
        assertThat(apartmentType1).isNotEqualTo(apartmentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApartmentTypeDTO.class);
        ApartmentTypeDTO apartmentTypeDTO1 = new ApartmentTypeDTO();
        apartmentTypeDTO1.setId(1L);
        ApartmentTypeDTO apartmentTypeDTO2 = new ApartmentTypeDTO();
        assertThat(apartmentTypeDTO1).isNotEqualTo(apartmentTypeDTO2);
        apartmentTypeDTO2.setId(apartmentTypeDTO1.getId());
        assertThat(apartmentTypeDTO1).isEqualTo(apartmentTypeDTO2);
        apartmentTypeDTO2.setId(2L);
        assertThat(apartmentTypeDTO1).isNotEqualTo(apartmentTypeDTO2);
        apartmentTypeDTO1.setId(null);
        assertThat(apartmentTypeDTO1).isNotEqualTo(apartmentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apartmentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apartmentTypeMapper.fromId(null)).isNull();
    }
}
