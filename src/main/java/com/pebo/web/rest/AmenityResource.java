package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.service.AmenityService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import com.pebo.web.rest.util.PaginationUtil;
import com.pebo.service.dto.AmenityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
     * @param amenityDTO the amenityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amenityDTO, or with status 400 (Bad Request) if the amenity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amenities")
    @Timed
    public ResponseEntity<AmenityDTO> createAmenity(@Valid @RequestBody AmenityDTO amenityDTO) throws URISyntaxException {
        log.debug("REST request to save Amenity : {}", amenityDTO);
        if (amenityDTO.getId() != null) {
            throw new BadRequestAlertException("A new amenity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmenityDTO result = amenityService.save(amenityDTO);
        return ResponseEntity.created(new URI("/api/amenities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amenities : Updates an existing amenity.
     *
     * @param amenityDTO the amenityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amenityDTO,
     * or with status 400 (Bad Request) if the amenityDTO is not valid,
     * or with status 500 (Internal Server Error) if the amenityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amenities")
    @Timed
    public ResponseEntity<AmenityDTO> updateAmenity(@Valid @RequestBody AmenityDTO amenityDTO) throws URISyntaxException {
        log.debug("REST request to update Amenity : {}", amenityDTO);
        if (amenityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmenityDTO result = amenityService.save(amenityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amenityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amenities : get all the amenities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amenities in body
     */
    @GetMapping("/amenities")
    @Timed
    public ResponseEntity<List<AmenityDTO>> getAllAmenities(Pageable pageable) {
        log.debug("REST request to get a page of Amenities");
        Page<AmenityDTO> page = amenityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amenities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amenities/:id : get the "id" amenity.
     *
     * @param id the id of the amenityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amenityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amenities/{id}")
    @Timed
    public ResponseEntity<AmenityDTO> getAmenity(@PathVariable Long id) {
        log.debug("REST request to get Amenity : {}", id);
        Optional<AmenityDTO> amenityDTO = amenityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amenityDTO);
    }

    /**
     * DELETE  /amenities/:id : delete the "id" amenity.
     *
     * @param id the id of the amenityDTO to delete
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
