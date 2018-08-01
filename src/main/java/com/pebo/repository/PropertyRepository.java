package com.pebo.repository;

import com.pebo.domain.Property;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

//    @Query("select properties from Property properties where properties.owner.login = ?#{principal.username}")
//    List<Property> findByOwnerIsCurrentUser();

}
