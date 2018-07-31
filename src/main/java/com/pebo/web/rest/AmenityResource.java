package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.domain.Amenity;
import com.pebo.service.AmenityService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Amenity.
 */
@RestController
@RequestMapping("/api")
public class AmenityResource {

    private final Logger log = LoggerFactory.getLogger(AmenityResource.class);

    private static final String ENTITY_NAME = "amenity";

    private final AmenityService amenityService;

    public AmenityResource(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    /**
     * POST  /amenities : Create a new amenity.
     *
     * @param amenity the amenity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amenity, or with status 400 (Bad Request) if the amenity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amenities")
    @Timed
    public ResponseEntity<Amenity> createAmenity(@Valid @RequestBody Amenity amenity) throws URISyntaxException {
        log.debug("REST request to save Amenity : {}", amenity);
        if (amenity.getId() != null) {
            throw new BadRequestAlertException("A new amenity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Amenity result = amenityService.save(amenity);
        return ResponseEntity.created(new URI("/api/amenities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amenities : Updates an existing amenity.
     *
     * @param amenity the amenity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amenity,
     * or with status 400 (Bad Request) if the amenity is not valid,
     * or with status 500 (Internal Server Error) if the amenity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amenities")
    @Timed
    public ResponseEntity<Amenity> updateAmenity(@Valid @RequestBody Amenity amenity) throws URISyntaxException {
        log.debug("REST request to update Amenity : {}", amenity);
        if (amenity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Amenity result = amenityService.save(amenity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amenity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amenities : get all the amenities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of amenities in body
     */
    @GetMapping("/amenities")
    @Timed
    public List<Amenity> getAllAmenities() {
        log.debug("REST request to get all Amenities");
        return amenityService.findAll();
    }

    /**
     * GET  /amenities/:id : get the "id" amenity.
     *
     * @param id the id of the amenity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amenity, or with status 404 (Not Found)
     */
    @GetMapping("/amenities/{id}")
    @Timed
    public ResponseEntity<Amenity> getAmenity(@PathVariable Long id) {
        log.debug("REST request to get Amenity : {}", id);
        Optional<Amenity> amenity = amenityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amenity);
    }

    /**
     * DELETE  /amenities/:id : delete the "id" amenity.
     *
     * @param id the id of the amenity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amenities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        log.debug("REST request to delete Amenity : {}", id);
        amenityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
