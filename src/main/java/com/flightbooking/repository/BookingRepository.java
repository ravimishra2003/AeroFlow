package com.flightbooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.entity.Booking;
import com.flightbooking.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByFlightId(Integer flightId);
	List<Booking> findByStatus(BookingStatus status);
	List<Booking> findByBookingDate(LocalDateTime date);
}
