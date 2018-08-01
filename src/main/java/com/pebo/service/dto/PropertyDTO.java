package com.pebo.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Property entity.
 */
public class PropertyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 50)
    private String contactName;

    @Size(max = 50)
    private String contactEmail;

    @Size(max = 30)
    private String phoneNumber;

    private Float rating;

    private Long ownerId;

    private String ownerLogin;

    private Long addressId;

    private String addressAddressLine1;

    private Long propertyTypeId;

    private String propertyTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressAddressLine1() {
        return addressAddressLine1;
    }

    public void setAddressAddressLine1(String addressAddressLine1) {
        this.addressAddressLine1 = addressAddressLine1;
    }

    public Long getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(Long propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if (propertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", rating=" + getRating() +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerLogin() + "'" +
            ", address=" + getAddressId() +
            ", address='" + getAddressAddressLine1() + "'" +
            ", propertyType=" + getPropertyTypeId() +
            ", propertyType='" + getPropertyTypeName() + "'" +
            "}";
    }
}
