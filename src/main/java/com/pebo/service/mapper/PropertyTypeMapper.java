package com.pebo.service.mapper;

import com.pebo.domain.*;
import com.pebo.service.dto.PropertyTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PropertyType and its DTO PropertyTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropertyTypeMapper extends EntityMapper<PropertyTypeDTO, PropertyType> {



    default PropertyType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropertyType propertyType = new PropertyType();
        propertyType.setId(id);
        return propertyType;
    }
}
