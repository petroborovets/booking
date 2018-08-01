package com.pebo.repository;

import com.pebo.domain.Amenity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Amenity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

}
