package com.pebo.service;

import com.pebo.domain.Amenity;
import com.pebo.repository.AmenityRepository;
import com.pebo.service.dto.AmenityDTO;
import com.pebo.service.mapper.AmenityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Amenity.
 */
@Service
@Transactional
public class AmenityService {

    private final Logger log = LoggerFactory.getLogger(AmenityService.class);

    private final AmenityRepository amenityRepository;

    private final AmenityMapper amenityMapper;

    public AmenityService(AmenityRepository amenityRepository, AmenityMapper amenityMapper) {
        this.amenityRepository = amenityRepository;
        this.amenityMapper = amenityMapper;
    }

    /**
     * Save a amenity.
     *
     * @param amenityDTO the entity to save
     * @return the persisted entity
     */
    public AmenityDTO save(AmenityDTO amenityDTO) {
        log.debug("Request to save Amenity : {}", amenityDTO);
        Amenity amenity = amenityMapper.toEntity(amenityDTO);
        amenity = amenityRepository.save(amenity);
        return amenityMapper.toDto(amenity);
    }

    /**
     * Get all the amenities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AmenityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amenities");
        return amenityRepository.findAll(pageable)
            .map(amenityMapper::toDto);
    }


    /**
     * Get one amenity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AmenityDTO> findOne(Long id) {
        log.debug("Request to get Amenity : {}", id);
        return amenityRepository.findById(id)
            .map(amenityMapper::toDto);
    }

    /**
     * Delete the amenity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Amenity : {}", id);
        amenityRepository.deleteById(id);
    }
}
