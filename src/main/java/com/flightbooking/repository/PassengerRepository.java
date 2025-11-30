package com.flightbooking.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	List<Passenger> findByBookingId(Integer bookingId);
	Optional<Passenger> findBookingByContactNumber(Long contactNumber);
}
