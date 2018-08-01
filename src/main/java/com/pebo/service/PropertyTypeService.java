package com.pebo.service;

import com.pebo.domain.PropertyType;
import com.pebo.repository.PropertyTypeRepository;
import com.pebo.service.dto.PropertyTypeDTO;
import com.pebo.service.mapper.PropertyTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing PropertyType.
 */
@Service
@Transactional
public class PropertyTypeService {

    private final Logger log = LoggerFactory.getLogger(PropertyTypeService.class);

    private final PropertyTypeRepository propertyTypeRepository;

    private final PropertyTypeMapper propertyTypeMapper;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository, PropertyTypeMapper propertyTypeMapper) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.propertyTypeMapper = propertyTypeMapper;
    }

    /**
     * Save a propertyType.
     *
     * @param propertyTypeDTO the entity to save
     * @return the persisted entity
     */
    public PropertyTypeDTO save(PropertyTypeDTO propertyTypeDTO) {
        log.debug("Request to save PropertyType : {}", propertyTypeDTO);
        PropertyType propertyType = propertyTypeMapper.toEntity(propertyTypeDTO);
        propertyType = propertyTypeRepository.save(propertyType);
        return propertyTypeMapper.toDto(propertyType);
    }

    /**
     * Get all the propertyTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropertyTypes");
        return propertyTypeRepository.findAll(pageable)
            .map(propertyTypeMapper::toDto);
    }


    /**
     * Get one propertyType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyTypeDTO> findOne(Long id) {
        log.debug("Request to get PropertyType : {}", id);
        return propertyTypeRepository.findById(id)
            .map(propertyTypeMapper::toDto);
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
