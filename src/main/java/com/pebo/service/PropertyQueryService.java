package com.pebo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pebo.domain.Property;
import com.pebo.domain.*; // for static metamodels
import com.pebo.repository.PropertyRepository;
import com.pebo.service.dto.PropertyCriteria;

import com.pebo.service.dto.PropertyDTO;
import com.pebo.service.mapper.PropertyMapper;

/**
 * Service for executing complex queries for Property entities in the database.
 * The main input is a {@link PropertyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropertyDTO} or a {@link Page} of {@link PropertyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropertyQueryService extends QueryService<Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyQueryService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyQueryService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Return a {@link List} of {@link PropertyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropertyDTO> findByCriteria(PropertyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Property> specification = createSpecification(criteria);
        return propertyMapper.toDto(propertyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropertyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropertyDTO> findByCriteria(PropertyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Property> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page)
            .map(propertyMapper::toDto);
    }

    /**
     * Function to convert PropertyCriteria to a {@link Specification}
     */
    private Specification<Property> createSpecification(PropertyCriteria criteria) {
        Specification<Property> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Property_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Property_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Property_.description));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), Property_.contactName));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), Property_.contactEmail));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Property_.phoneNumber));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), Property_.rating));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), Property_.owner, User_.id));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAddressId(), Property_.address, Address_.id));
            }
            if (criteria.getPropertyTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPropertyTypeId(), Property_.propertyType, PropertyType_.id));
            }
        }
        return specification;
    }

}
