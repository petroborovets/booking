package com.pebo.service;

import com.pebo.domain.PropertyType;
import com.pebo.repository.PropertyTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing PropertyType.
 */
@Service
@Transactional
public class PropertyTypeService {

    private final Logger log = LoggerFactory.getLogger(PropertyTypeService.class);

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    /**
     * Save a propertyType.
     *
     * @param propertyType the entity to save
     * @return the persisted entity
     */
    public PropertyType save(PropertyType propertyType) {
        log.debug("Request to save PropertyType : {}", propertyType);        return propertyTypeRepository.save(propertyType);
    }

    /**
     * Get all the propertyTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PropertyType> findAll() {
        log.debug("Request to get all PropertyTypes");
        return propertyTypeRepository.findAll();
    }


    /**
     * Get one propertyType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyType> findOne(Long id) {
        log.debug("Request to get PropertyType : {}", id);
        return propertyTypeRepository.findById(id);
    }

    /**
     * Delete the propertyType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PropertyType : {}", id);
        propertyTypeRepository.deleteById(id);
    }
}
