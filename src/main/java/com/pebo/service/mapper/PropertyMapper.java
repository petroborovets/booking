package com.pebo.service.mapper;

import com.pebo.domain.*;
import com.pebo.service.dto.PropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class, PropertyTypeMapper.class})
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.addressLine1", target = "addressAddressLine1")
    @Mapping(source = "propertyType.id", target = "propertyTypeId")
    @Mapping(source = "propertyType.name", target = "propertyTypeName")
    PropertyDTO toDto(Property property);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "propertyTypeId", target = "propertyType")
    Property toEntity(PropertyDTO propertyDTO);

    default Property fromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
