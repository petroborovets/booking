package com.pebo.service;

import com.pebo.domain.BookingStatus;
import com.pebo.repository.BookingStatusRepository;
import com.pebo.service.dto.BookingStatusDTO;
import com.pebo.service.mapper.BookingStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing BookingStatus.
 */
@Service
@Transactional
public class BookingStatusService {

    private final Logger log = LoggerFactory.getLogger(BookingStatusService.class);

    private final BookingStatusRepository bookingStatusRepository;

    private final BookingStatusMapper bookingStatusMapper;

    public BookingStatusService(BookingStatusRepository bookingStatusRepository, BookingStatusMapper bookingStatusMapper) {
        this.bookingStatusRepository = bookingStatusRepository;
        this.bookingStatusMapper = bookingStatusMapper;
    }

    /**
     * Save a bookingStatus.
     *
     * @param bookingStatusDTO the entity to save
     * @return the persisted entity
     */
    public BookingStatusDTO save(BookingStatusDTO bookingStatusDTO) {
        log.debug("Request to save BookingStatus : {}", bookingStatusDTO);
        BookingStatus bookingStatus = bookingStatusMapper.toEntity(bookingStatusDTO);
        bookingStatus = bookingStatusRepository.save(bookingStatus);
        return bookingStatusMapper.toDto(bookingStatus);
    }

    /**
     * Get all the bookingStatuses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BookingStatusDTO> findAll() {
        log.debug("Request to get all BookingStatuses");
        return bookingStatusRepository.findAll().stream()
            .map(bookingStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bookingStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BookingStatusDTO> findOne(Long id) {
        log.debug("Request to get BookingStatus : {}", id);
        return bookingStatusRepository.findById(id)
            .map(bookingStatusMapper::toDto);
    }

    /**
     * Delete the bookingStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BookingStatus : {}", id);
        bookingStatusRepository.deleteById(id);
    }
}
