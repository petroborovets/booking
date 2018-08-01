package com.pebo.service;

import com.pebo.domain.ApartmentType;
import com.pebo.repository.ApartmentTypeRepository;
import com.pebo.service.dto.ApartmentTypeDTO;
import com.pebo.service.mapper.ApartmentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ApartmentType.
 */
@Service
@Transactional
public class ApartmentTypeService {

    private final Logger log = LoggerFactory.getLogger(ApartmentTypeService.class);

    private final ApartmentTypeRepository apartmentTypeRepository;

    private final ApartmentTypeMapper apartmentTypeMapper;

    public ApartmentTypeService(ApartmentTypeRepository apartmentTypeRepository, ApartmentTypeMapper apartmentTypeMapper) {
        this.apartmentTypeRepository = apartmentTypeRepository;
        this.apartmentTypeMapper = apartmentTypeMapper;
    }

    /**
     * Save a apartmentType.
     *
     * @param apartmentTypeDTO the entity to save
     * @return the persisted entity
     */
    public ApartmentTypeDTO save(ApartmentTypeDTO apartmentTypeDTO) {
        log.debug("Request to save ApartmentType : {}", apartmentTypeDTO);
        ApartmentType apartmentType = apartmentTypeMapper.toEntity(apartmentTypeDTO);
        apartmentType = apartmentTypeRepository.save(apartmentType);
        return apartmentTypeMapper.toDto(apartmentType);
    }

    /**
     * Get all the apartmentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApartmentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApartmentTypes");
        return apartmentTypeRepository.findAll(pageable)
            .map(apartmentTypeMapper::toDto);
    }


    /**
     * Get one apartmentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApartmentTypeDTO> findOne(Long id) {
        log.debug("Request to get ApartmentType : {}", id);
        return apartmentTypeRepository.findById(id)
            .map(apartmentTypeMapper::toDto);
    }

    /**
     * Delete the apartmentType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApartmentType : {}", id);
        apartmentTypeRepository.deleteById(id);
    }
}
