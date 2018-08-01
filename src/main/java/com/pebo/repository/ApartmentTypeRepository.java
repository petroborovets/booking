package com.pebo.repository;

import com.pebo.domain.ApartmentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApartmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartmentTypeRepository extends JpaRepository<ApartmentType, Long> {

}
