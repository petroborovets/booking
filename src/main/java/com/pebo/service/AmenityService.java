package com.pebo.service;

import com.pebo.domain.Amenity;
import com.pebo.repository.AmenityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Amenity.
 */
@Service
@Transactional
public class AmenityService {

    private final Logger log = LoggerFactory.getLogger(AmenityService.class);

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    /**
     * Save a amenity.
     *
     * @param amenity the entity to save
     * @return the persisted entity
     */
    public Amenity save(Amenity amenity) {
        log.debug("Request to save Amenity : {}", amenity);        return amenityRepository.save(amenity);
    }

    /**
     * Get all the amenities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Amenity> findAll() {
        log.debug("Request to get all Amenities");
        return amenityRepository.findAll();
    }


    /**
     * Get one amenity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Amenity> findOne(Long id) {
        log.debug("Request to get Amenity : {}", id);
        return amenityRepository.findById(id);
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
