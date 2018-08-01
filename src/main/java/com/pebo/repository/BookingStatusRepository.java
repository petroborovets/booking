package com.pebo.repository;

import com.pebo.domain.BookingStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {

}
