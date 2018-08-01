package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.service.ApartmentTypeService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import com.pebo.web.rest.util.PaginationUtil;
import com.pebo.service.dto.ApartmentTypeDTO;
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
 * REST controller for managing ApartmentType.
 */
@RestController
@RequestMapping("/api")
public class ApartmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ApartmentTypeResource.class);

    private static final String ENTITY_NAME = "apartmentType";

    private final ApartmentTypeService apartmentTypeService;

    public ApartmentTypeResource(ApartmentTypeService apartmentTypeService) {
        this.apartmentTypeService = apartmentTypeService;
    }

    /**
     * POST  /apartment-types : Create a new apartmentType.
     *
     * @param apartmentTypeDTO the apartmentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apartmentTypeDTO, or with status 400 (Bad Request) if the apartmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apartment-types")
    @Timed
    public ResponseEntity<ApartmentTypeDTO> createApartmentType(@Valid @RequestBody ApartmentTypeDTO apartmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ApartmentType : {}", apartmentTypeDTO);
        if (apartmentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new apartmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApartmentTypeDTO result = apartmentTypeService.save(apartmentTypeDTO);
        return ResponseEntity.created(new URI("/api/apartment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apartment-types : Updates an existing apartmentType.
     *
     * @param apartmentTypeDTO the apartmentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apartmentTypeDTO,
     * or with status 400 (Bad Request) if the apartmentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the apartmentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apartment-types")
    @Timed
    public ResponseEntity<ApartmentTypeDTO> updateApartmentType(@Valid @RequestBody ApartmentTypeDTO apartmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ApartmentType : {}", apartmentTypeDTO);
        if (apartmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApartmentTypeDTO result = apartmentTypeService.save(apartmentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apartmentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apartment-types : get all the apartmentTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apartmentTypes in body
     */
    @GetMapping("/apartment-types")
    @Timed
    public ResponseEntity<List<ApartmentTypeDTO>> getAllApartmentTypes(Pageable pageable) {
        log.debug("REST request to get a page of ApartmentTypes");
        Page<ApartmentTypeDTO> page = apartmentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apartment-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apartment-types/:id : get the "id" apartmentType.
     *
     * @param id the id of the apartmentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apartmentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/apartment-types/{id}")
    @Timed
    public ResponseEntity<ApartmentTypeDTO> getApartmentType(@PathVariable Long id) {
        log.debug("REST request to get ApartmentType : {}", id);
        Optional<ApartmentTypeDTO> apartmentTypeDTO = apartmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apartmentTypeDTO);
    }

    /**
     * DELETE  /apartment-types/:id : delete the "id" apartmentType.
     *
     * @param id the id of the apartmentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apartment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteApartmentType(@PathVariable Long id) {
        log.debug("REST request to delete ApartmentType : {}", id);
        apartmentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
