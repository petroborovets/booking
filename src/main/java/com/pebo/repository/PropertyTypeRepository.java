package com.pebo.repository;

import com.pebo.domain.PropertyType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropertyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, Long> {

}
