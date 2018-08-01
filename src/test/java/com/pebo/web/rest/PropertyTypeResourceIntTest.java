package com.pebo.web.rest;

import com.pebo.BookingApp;

import com.pebo.domain.PropertyType;
import com.pebo.repository.PropertyTypeRepository;
import com.pebo.service.PropertyTypeService;
import com.pebo.service.dto.PropertyTypeDTO;
import com.pebo.service.mapper.PropertyTypeMapper;
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
 * Test class for the PropertyTypeResource REST controller.
 *
 * @see PropertyTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class PropertyTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;


    @Autowired
    private PropertyTypeMapper propertyTypeMapper;
    

    @Autowired
    private PropertyTypeService propertyTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyTypeMockMvc;

    private PropertyType propertyType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyTypeResource propertyTypeResource = new PropertyTypeResource(propertyTypeService);
        this.restPropertyTypeMockMvc = MockMvcBuilders.standaloneSetup(propertyTypeResource)
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
    public static PropertyType createEntity(EntityManager em) {
        PropertyType propertyType = new PropertyType()
            .name(DEFAULT_NAME);
        return propertyType;
    }

    @Before
    public void initTest() {
        propertyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropertyType() throws Exception {
        int databaseSizeBeforeCreate = propertyTypeRepository.findAll().size();

        // Create the PropertyType
        PropertyTypeDTO propertyTypeDTO = propertyTypeMapper.toDto(propertyType);
        restPropertyTypeMockMvc.perform(post("/api/property-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PropertyType in the database
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PropertyType testPropertyType = propertyTypeList.get(propertyTypeList.size() - 1);
        assertThat(testPropertyType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPropertyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyTypeRepository.findAll().size();

        // Create the PropertyType with an existing ID
        propertyType.setId(1L);
        PropertyTypeDTO propertyTypeDTO = propertyTypeMapper.toDto(propertyType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyTypeMockMvc.perform(post("/api/property-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyType in the database
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyTypeRepository.findAll().size();
        // set the field null
        propertyType.setName(null);

        // Create the PropertyType, which fails.
        PropertyTypeDTO propertyTypeDTO = propertyTypeMapper.toDto(propertyType);

        restPropertyTypeMockMvc.perform(post("/api/property-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPropertyTypes() throws Exception {
        // Initialize the database
        propertyTypeRepository.saveAndFlush(propertyType);

        // Get all the propertyTypeList
        restPropertyTypeMockMvc.perform(get("/api/property-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getPropertyType() throws Exception {
        // Initialize the database
        propertyTypeRepository.saveAndFlush(propertyType);

        // Get the propertyType
        restPropertyTypeMockMvc.perform(get("/api/property-types/{id}", propertyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propertyType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPropertyType() throws Exception {
        // Get the propertyType
        restPropertyTypeMockMvc.perform(get("/api/property-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropertyType() throws Exception {
        // Initialize the database
        propertyTypeRepository.saveAndFlush(propertyType);

        int databaseSizeBeforeUpdate = propertyTypeRepository.findAll().size();

        // Update the propertyType
        PropertyType updatedPropertyType = propertyTypeRepository.findById(propertyType.getId()).get();
        // Disconnect from session so that the updates on updatedPropertyType are not directly saved in db
        em.detach(updatedPropertyType);
        updatedPropertyType
            .name(UPDATED_NAME);
        PropertyTypeDTO propertyTypeDTO = propertyTypeMapper.toDto(updatedPropertyType);

        restPropertyTypeMockMvc.perform(put("/api/property-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PropertyType in the database
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeUpdate);
        PropertyType testPropertyType = propertyTypeList.get(propertyTypeList.size() - 1);
        assertThat(testPropertyType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPropertyType() throws Exception {
        int databaseSizeBeforeUpdate = propertyTypeRepository.findAll().size();

        // Create the PropertyType
        PropertyTypeDTO propertyTypeDTO = propertyTypeMapper.toDto(propertyType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertyTypeMockMvc.perform(put("/api/property-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyType in the database
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropertyType() throws Exception {
        // Initialize the database
        propertyTypeRepository.saveAndFlush(propertyType);

        int databaseSizeBeforeDelete = propertyTypeRepository.findAll().size();

        // Get the propertyType
        restPropertyTypeMockMvc.perform(delete("/api/property-types/{id}", propertyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAll();
        assertThat(propertyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyType.class);
        PropertyType propertyType1 = new PropertyType();
        propertyType1.setId(1L);
        PropertyType propertyType2 = new PropertyType();
        propertyType2.setId(propertyType1.getId());
        assertThat(propertyType1).isEqualTo(propertyType2);
        propertyType2.setId(2L);
        assertThat(propertyType1).isNotEqualTo(propertyType2);
        propertyType1.setId(null);
        assertThat(propertyType1).isNotEqualTo(propertyType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyTypeDTO.class);
        PropertyTypeDTO propertyTypeDTO1 = new PropertyTypeDTO();
        propertyTypeDTO1.setId(1L);
        PropertyTypeDTO propertyTypeDTO2 = new PropertyTypeDTO();
        assertThat(propertyTypeDTO1).isNotEqualTo(propertyTypeDTO2);
        propertyTypeDTO2.setId(propertyTypeDTO1.getId());
        assertThat(propertyTypeDTO1).isEqualTo(propertyTypeDTO2);
        propertyTypeDTO2.setId(2L);
        assertThat(propertyTypeDTO1).isNotEqualTo(propertyTypeDTO2);
        propertyTypeDTO1.setId(null);
        assertThat(propertyTypeDTO1).isNotEqualTo(propertyTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertyTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertyTypeMapper.fromId(null)).isNull();
    }
}
