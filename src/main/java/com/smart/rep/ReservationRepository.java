package com.smart.rep;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Page<Reservation> findAllByUserId(Pageable pageable,Long userid);
}
