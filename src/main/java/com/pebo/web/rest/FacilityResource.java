package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.domain.Facility;
import com.pebo.service.FacilityService;
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
 * REST controller for managing Facility.
 */
@RestController
@RequestMapping("/api")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(FacilityResource.class);

    private static final String ENTITY_NAME = "facility";

    private final FacilityService facilityService;

    public FacilityResource(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * POST  /facilities : Create a new facility.
     *
     * @param facility the facility to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facility, or with status 400 (Bad Request) if the facility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facilities")
    @Timed
    public ResponseEntity<Facility> createFacility(@Valid @RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to save Facility : {}", facility);
        if (facility.getId() != null) {
            throw new BadRequestAlertException("A new facility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facility result = facilityService.save(facility);
        return ResponseEntity.created(new URI("/api/facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facilities : Updates an existing facility.
     *
     * @param facility the facility to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facility,
     * or with status 400 (Bad Request) if the facility is not valid,
     * or with status 500 (Internal Server Error) if the facility couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facilities")
    @Timed
    public ResponseEntity<Facility> updateFacility(@Valid @RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to update Facility : {}", facility);
        if (facility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Facility result = facilityService.save(facility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facility.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facilities : get all the facilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilities in body
     */
    @GetMapping("/facilities")
    @Timed
    public List<Facility> getAllFacilities() {
        log.debug("REST request to get all Facilities");
        return facilityService.findAll();
    }

    /**
     * GET  /facilities/:id : get the "id" facility.
     *
     * @param id the id of the facility to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facility, or with status 404 (Not Found)
     */
    @GetMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<Facility> getFacility(@PathVariable Long id) {
        log.debug("REST request to get Facility : {}", id);
        Optional<Facility> facility = facilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facility);
    }

    /**
     * DELETE  /facilities/:id : delete the "id" facility.
     *
     * @param id the id of the facility to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("REST request to delete Facility : {}", id);
        facilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
