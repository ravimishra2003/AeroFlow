package com.flightbooking.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Flight;
import com.flightbooking.repository.FlightRepository;


@Repository
public class FlightDao {
	@Autowired
	private FlightRepository flightRepo;
	public Flight addFlight(Flight flight) {
		return flightRepo.save(flight);
	}
	public List<Flight> getAllFlights() {
	    return flightRepo.findAll();
	}
	public Optional<Flight> getFlightById(Integer id) {
		return flightRepo.findById(id);
	}
	public List<Flight> getFlightBySourceAndDestination(String source, String destination){
		return flightRepo.findBySourceAndDestination(source, destination);
	}
	public List<Flight> getFlightByAirline(String airline){
		return flightRepo.findByAirline(airline);
	}
	public Flight updateFlight(Flight flight) {
		return flightRepo.save(flight);
	}
	public void deleteFlight(Integer id) {
		flightRepo.deleteById(id);
	}
	public Page<Flight> getFlightsByPageAndSort(int pageNumber, int pageSize,String field){
		return flightRepo.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).descending()));
	}	
}
