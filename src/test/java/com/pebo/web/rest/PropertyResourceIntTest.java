package com.pebo.web.rest;

import com.pebo.BookingApp;

import com.pebo.domain.Property;
import com.pebo.domain.User;
import com.pebo.domain.Address;
import com.pebo.domain.PropertyType;
import com.pebo.repository.PropertyRepository;
import com.pebo.service.PropertyService;
import com.pebo.service.dto.PropertyDTO;
import com.pebo.service.mapper.PropertyMapper;
import com.pebo.web.rest.errors.ExceptionTranslator;
import com.pebo.service.dto.PropertyCriteria;
import com.pebo.service.PropertyQueryService;

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
 * Test class for the PropertyResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class PropertyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;

    @Autowired
    private PropertyRepository propertyRepository;


    @Autowired
    private PropertyMapper propertyMapper;
    

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyQueryService propertyQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyMockMvc;

    private Property property;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyResource propertyResource = new PropertyResource(propertyService, propertyQueryService);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
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
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .contactName(DEFAULT_CONTACT_NAME)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .rating(DEFAULT_RATING);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        property.setOwner(user);
        // Add required entity
        PropertyType propertyType = PropertyTypeResourceIntTest.createEntity(em);
        em.persist(propertyType);
        em.flush();
        property.setPropertyType(propertyType);
        return property;
    }

    @Before
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testProperty.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testProperty.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProperty.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId(1L);
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setName(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllPropertiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name equals to DEFAULT_NAME
        defaultPropertyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the propertyList where name equals to UPDATED_NAME
        defaultPropertyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPropertiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPropertyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the propertyList where name equals to UPDATED_NAME
        defaultPropertyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPropertiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name is not null
        defaultPropertyShouldBeFound("name.specified=true");

        // Get all the propertyList where name is null
        defaultPropertyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description equals to DEFAULT_DESCRIPTION
        defaultPropertyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the propertyList where description equals to UPDATED_DESCRIPTION
        defaultPropertyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPropertyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the propertyList where description equals to UPDATED_DESCRIPTION
        defaultPropertyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description is not null
        defaultPropertyShouldBeFound("description.specified=true");

        // Get all the propertyList where description is null
        defaultPropertyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactName equals to DEFAULT_CONTACT_NAME
        defaultPropertyShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the propertyList where contactName equals to UPDATED_CONTACT_NAME
        defaultPropertyShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultPropertyShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the propertyList where contactName equals to UPDATED_CONTACT_NAME
        defaultPropertyShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactName is not null
        defaultPropertyShouldBeFound("contactName.specified=true");

        // Get all the propertyList where contactName is null
        defaultPropertyShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultPropertyShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the propertyList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultPropertyShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultPropertyShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the propertyList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultPropertyShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPropertiesByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contactEmail is not null
        defaultPropertyShouldBeFound("contactEmail.specified=true");

        // Get all the propertyList where contactEmail is null
        defaultPropertyShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPropertyShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the propertyList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPropertyShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPropertyShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the propertyList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPropertyShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where phoneNumber is not null
        defaultPropertyShouldBeFound("phoneNumber.specified=true");

        // Get all the propertyList where phoneNumber is null
        defaultPropertyShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where rating equals to DEFAULT_RATING
        defaultPropertyShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the propertyList where rating equals to UPDATED_RATING
        defaultPropertyShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllPropertiesByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultPropertyShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the propertyList where rating equals to UPDATED_RATING
        defaultPropertyShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllPropertiesByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where rating is not null
        defaultPropertyShouldBeFound("rating.specified=true");

        // Get all the propertyList where rating is null
        defaultPropertyShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        property.setOwner(owner);
        propertyRepository.saveAndFlush(property);
        Long ownerId = owner.getId();

        // Get all the propertyList where owner equals to ownerId
        defaultPropertyShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the propertyList where owner equals to ownerId + 1
        defaultPropertyShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllPropertiesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        Address address = AddressResourceIntTest.createEntity(em);
        em.persist(address);
        em.flush();
        property.setAddress(address);
        propertyRepository.saveAndFlush(property);
        Long addressId = address.getId();

        // Get all the propertyList where address equals to addressId
        defaultPropertyShouldBeFound("addressId.equals=" + addressId);

        // Get all the propertyList where address equals to addressId + 1
        defaultPropertyShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllPropertiesByPropertyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        PropertyType propertyType = PropertyTypeResourceIntTest.createEntity(em);
        em.persist(propertyType);
        em.flush();
        property.setPropertyType(propertyType);
        propertyRepository.saveAndFlush(property);
        Long propertyTypeId = propertyType.getId();

        // Get all the propertyList where propertyType equals to propertyTypeId
        defaultPropertyShouldBeFound("propertyTypeId.equals=" + propertyTypeId);

        // Get all the propertyList where propertyType equals to propertyTypeId + 1
        defaultPropertyShouldNotBeFound("propertyTypeId.equals=" + (propertyTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPropertyShouldBeFound(String filter) throws Exception {
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPropertyShouldNotBeFound(String filter) throws Exception {
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).get();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactName(UPDATED_CONTACT_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .rating(UPDATED_RATING);
        PropertyDTO propertyDTO = propertyMapper.toDto(updatedProperty);

        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testProperty.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testProperty.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProperty.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = new Property();
        property1.setId(1L);
        Property property2 = new Property();
        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);
        property2.setId(2L);
        assertThat(property1).isNotEqualTo(property2);
        property1.setId(null);
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyDTO.class);
        PropertyDTO propertyDTO1 = new PropertyDTO();
        propertyDTO1.setId(1L);
        PropertyDTO propertyDTO2 = new PropertyDTO();
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO2.setId(propertyDTO1.getId());
        assertThat(propertyDTO1).isEqualTo(propertyDTO2);
        propertyDTO2.setId(2L);
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO1.setId(null);
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertyMapper.fromId(null)).isNull();
    }
}
