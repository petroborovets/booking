package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.service.PropertyTypeService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import com.pebo.web.rest.util.PaginationUtil;
import com.pebo.service.dto.PropertyTypeDTO;
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
     * @param propertyTypeDTO the propertyTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyTypeDTO, or with status 400 (Bad Request) if the propertyType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-types")
    @Timed
    public ResponseEntity<PropertyTypeDTO> createPropertyType(@Valid @RequestBody PropertyTypeDTO propertyTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PropertyType : {}", propertyTypeDTO);
        if (propertyTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new propertyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyTypeDTO result = propertyTypeService.save(propertyTypeDTO);
        return ResponseEntity.created(new URI("/api/property-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-types : Updates an existing propertyType.
     *
     * @param propertyTypeDTO the propertyTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyTypeDTO,
     * or with status 400 (Bad Request) if the propertyTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertyTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-types")
    @Timed
    public ResponseEntity<PropertyTypeDTO> updatePropertyType(@Valid @RequestBody PropertyTypeDTO propertyTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PropertyType : {}", propertyTypeDTO);
        if (propertyTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyTypeDTO result = propertyTypeService.save(propertyTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-types : get all the propertyTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of propertyTypes in body
     */
    @GetMapping("/property-types")
    @Timed
    public ResponseEntity<List<PropertyTypeDTO>> getAllPropertyTypes(Pageable pageable) {
        log.debug("REST request to get a page of PropertyTypes");
        Page<PropertyTypeDTO> page = propertyTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /property-types/:id : get the "id" propertyType.
     *
     * @param id the id of the propertyTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/property-types/{id}")
    @Timed
    public ResponseEntity<PropertyTypeDTO> getPropertyType(@PathVariable Long id) {
        log.debug("REST request to get PropertyType : {}", id);
        Optional<PropertyTypeDTO> propertyTypeDTO = propertyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyTypeDTO);
    }

    /**
     * DELETE  /property-types/:id : delete the "id" propertyType.
     *
     * @param id the id of the propertyTypeDTO to delete
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
