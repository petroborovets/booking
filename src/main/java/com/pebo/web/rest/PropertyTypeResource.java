package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.domain.PropertyType;
import com.pebo.service.PropertyTypeService;
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
 * REST controller for managing PropertyType.
 */
@RestController
@RequestMapping("/api")
public class PropertyTypeResource {

    private final Logger log = LoggerFactory.getLogger(PropertyTypeResource.class);

    private static final String ENTITY_NAME = "propertyType";

    private final PropertyTypeService propertyTypeService;

    public PropertyTypeResource(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    /**
     * POST  /property-types : Create a new propertyType.
     *
     * @param propertyType the propertyType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyType, or with status 400 (Bad Request) if the propertyType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-types")
    @Timed
    public ResponseEntity<PropertyType> createPropertyType(@Valid @RequestBody PropertyType propertyType) throws URISyntaxException {
        log.debug("REST request to save PropertyType : {}", propertyType);
        if (propertyType.getId() != null) {
            throw new BadRequestAlertException("A new propertyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyType result = propertyTypeService.save(propertyType);
        return ResponseEntity.created(new URI("/api/property-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-types : Updates an existing propertyType.
     *
     * @param propertyType the propertyType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyType,
     * or with status 400 (Bad Request) if the propertyType is not valid,
     * or with status 500 (Internal Server Error) if the propertyType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-types")
    @Timed
    public ResponseEntity<PropertyType> updatePropertyType(@Valid @RequestBody PropertyType propertyType) throws URISyntaxException {
        log.debug("REST request to update PropertyType : {}", propertyType);
        if (propertyType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyType result = propertyTypeService.save(propertyType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-types : get all the propertyTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of propertyTypes in body
     */
    @GetMapping("/property-types")
    @Timed
    public List<PropertyType> getAllPropertyTypes() {
        log.debug("REST request to get all PropertyTypes");
        return propertyTypeService.findAll();
    }

    /**
     * GET  /property-types/:id : get the "id" propertyType.
     *
     * @param id the id of the propertyType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyType, or with status 404 (Not Found)
     */
    @GetMapping("/property-types/{id}")
    @Timed
    public ResponseEntity<PropertyType> getPropertyType(@PathVariable Long id) {
        log.debug("REST request to get PropertyType : {}", id);
        Optional<PropertyType> propertyType = propertyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyType);
    }

    /**
     * DELETE  /property-types/:id : delete the "id" propertyType.
     *
     * @param id the id of the propertyType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyType(@PathVariable Long id) {
        log.debug("REST request to delete PropertyType : {}", id);
        propertyTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
