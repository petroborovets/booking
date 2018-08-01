package com.pebo.service.mapper;

import com.pebo.domain.*;
import com.pebo.service.dto.BookingStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookingStatus and its DTO BookingStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookingStatusMapper extends EntityMapper<BookingStatusDTO, BookingStatus> {



    default BookingStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookingStatus bookingStatus = new BookingStatus();
        bookingStatus.setId(id);
        return bookingStatus;
    }
}
