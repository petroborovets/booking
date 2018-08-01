package com.pebo.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Property entity. This class is used in PropertyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /properties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropertyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter contactName;

    private StringFilter contactEmail;

    private StringFilter phoneNumber;

    private FloatFilter rating;

    private LongFilter ownerId;

    private LongFilter addressId;

    private LongFilter propertyTypeId;

    public PropertyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public FloatFilter getRating() {
        return rating;
    }

    public void setRating(FloatFilter rating) {
        this.rating = rating;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(LongFilter propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    @Override
    public String toString() {
        return "PropertyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (contactName != null ? "contactName=" + contactName + ", " : "") +
                (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (propertyTypeId != null ? "propertyTypeId=" + propertyTypeId + ", " : "") +
            "}";
    }

}
