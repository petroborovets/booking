package com.pebo.service.mapper;

import com.pebo.domain.*;
import com.pebo.service.dto.ApartmentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApartmentType and its DTO ApartmentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApartmentTypeMapper extends EntityMapper<ApartmentTypeDTO, ApartmentType> {



    default ApartmentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(id);
        return apartmentType;
    }
}
