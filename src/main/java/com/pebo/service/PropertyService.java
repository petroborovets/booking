package com.pebo.service;

import com.pebo.domain.Property;
import com.pebo.repository.PropertyRepository;
import com.pebo.service.dto.PropertyDTO;
import com.pebo.service.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save
     * @return the persisted entity
     */
    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    /**
     * Get all the properties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertyRepository.findAll(pageable)
            .map(propertyMapper::toDto);
    }


    /**
     * Get one property by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyDTO> findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findById(id)
            .map(propertyMapper::toDto);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }
}
