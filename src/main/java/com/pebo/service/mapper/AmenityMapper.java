package com.pebo.service.mapper;

import com.pebo.domain.*;
import com.pebo.service.dto.AmenityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amenity and its DTO AmenityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmenityMapper extends EntityMapper<AmenityDTO, Amenity> {



    default Amenity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amenity amenity = new Amenity();
        amenity.setId(id);
        return amenity;
    }
}
