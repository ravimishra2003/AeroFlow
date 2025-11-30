package com.flightbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Flight;
@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer>{
	List<Flight> findBySourceAndDestination(String source, String destination);

	List<Flight> findByAirline(String airline);
}
