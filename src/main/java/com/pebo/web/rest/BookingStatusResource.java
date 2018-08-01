package com.pebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pebo.service.BookingStatusService;
import com.pebo.web.rest.errors.BadRequestAlertException;
import com.pebo.web.rest.util.HeaderUtil;
import com.pebo.service.dto.BookingStatusDTO;
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
 * REST controller for managing BookingStatus.
 */
@RestController
@RequestMapping("/api")
public class BookingStatusResource {

    private final Logger log = LoggerFactory.getLogger(BookingStatusResource.class);

    private static final String ENTITY_NAME = "bookingStatus";

    private final BookingStatusService bookingStatusService;

    public BookingStatusResource(BookingStatusService bookingStatusService) {
        this.bookingStatusService = bookingStatusService;
    }

    /**
     * POST  /booking-statuses : Create a new bookingStatus.
     *
     * @param bookingStatusDTO the bookingStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookingStatusDTO, or with status 400 (Bad Request) if the bookingStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/booking-statuses")
    @Timed
    public ResponseEntity<BookingStatusDTO> createBookingStatus(@Valid @RequestBody BookingStatusDTO bookingStatusDTO) throws URISyntaxException {
        log.debug("REST request to save BookingStatus : {}", bookingStatusDTO);
        if (bookingStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookingStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookingStatusDTO result = bookingStatusService.save(bookingStatusDTO);
        return ResponseEntity.created(new URI("/api/booking-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /booking-statuses : Updates an existing bookingStatus.
     *
     * @param bookingStatusDTO the bookingStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookingStatusDTO,
     * or with status 400 (Bad Request) if the bookingStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookingStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/booking-statuses")
    @Timed
    public ResponseEntity<BookingStatusDTO> updateBookingStatus(@Valid @RequestBody BookingStatusDTO bookingStatusDTO) throws URISyntaxException {
        log.debug("REST request to update BookingStatus : {}", bookingStatusDTO);
        if (bookingStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookingStatusDTO result = bookingStatusService.save(bookingStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookingStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /booking-statuses : get all the bookingStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookingStatuses in body
     */
    @GetMapping("/booking-statuses")
    @Timed
    public List<BookingStatusDTO> getAllBookingStatuses() {
        log.debug("REST request to get all BookingStatuses");
        return bookingStatusService.findAll();
    }

    /**
     * GET  /booking-statuses/:id : get the "id" bookingStatus.
     *
     * @param id the id of the bookingStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookingStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/booking-statuses/{id}")
    @Timed
    public ResponseEntity<BookingStatusDTO> getBookingStatus(@PathVariable Long id) {
        log.debug("REST request to get BookingStatus : {}", id);
        Optional<BookingStatusDTO> bookingStatusDTO = bookingStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookingStatusDTO);
    }

    /**
     * DELETE  /booking-statuses/:id : delete the "id" bookingStatus.
     *
     * @param id the id of the bookingStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/booking-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookingStatus(@PathVariable Long id) {
        log.debug("REST request to delete BookingStatus : {}", id);
        bookingStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
