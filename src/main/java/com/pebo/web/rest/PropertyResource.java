package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.service.PropertyService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import com.pebo.web.rest.util.PaginationUtil;
import com.pebo.service.dto.PropertyDTO;
import com.pebo.service.dto.PropertyCriteria;
import com.pebo.service.PropertyQueryService;
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
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private static final String ENTITY_NAME = "property";

    private final PropertyService propertyService;

    private final PropertyQueryService propertyQueryService;

    public PropertyResource(PropertyService propertyService, PropertyQueryService propertyQueryService) {
        this.propertyService = propertyService;
        this.propertyQueryService = propertyQueryService;
    }

    /**
     * POST  /properties : Create a new property.
     *
     * @param propertyDTO the propertyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyDTO, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/properties")
    @Timed
    public ResponseEntity<PropertyDTO> createProperty(@Valid @RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);
        if (propertyDTO.getId() != null) {
            throw new BadRequestAlertException("A new property cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyDTO result = propertyService.save(propertyDTO);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /properties : Updates an existing property.
     *
     * @param propertyDTO the propertyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyDTO,
     * or with status 400 (Bad Request) if the propertyDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/properties")
    @Timed
    public ResponseEntity<PropertyDTO> updateProperty(@Valid @RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyDTO result = propertyService.save(propertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /properties : get all the properties.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/properties")
    @Timed
    public ResponseEntity<List<PropertyDTO>> getAllProperties(PropertyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Properties by criteria: {}", criteria);
        Page<PropertyDTO> page = propertyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/properties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /properties/:id : get the "id" property.
     *
     * @param id the id of the propertyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/properties/{id}")
    @Timed
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        Optional<PropertyDTO> propertyDTO = propertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }

    /**
     * DELETE  /properties/:id : delete the "id" property.
     *
     * @param id the id of the propertyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
