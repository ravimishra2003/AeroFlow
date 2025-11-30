package com.flightbooking.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flightbooking.dao.FlightDao;
import com.flightbooking.entity.Flight;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.exception.NoRecordException;
@Service
public class FlightService {
	@Autowired
	private FlightDao flightDao;
	public Flight addFlight(Flight flight){
		return flightDao.addFlight(flight);
	}
	public List<Flight> getAllFlights() {
	    return flightDao.getAllFlights();
	}
	public Flight getFlightById(Integer id) {
		Optional<Flight> flight=flightDao.getFlightById(id);
		if(flight.isPresent()) {
			return flight.get();
		}
		throw new FlightNotFoundException("Flight not found with id "+id);
	}
	public List<Flight> getFlightsBySourceAndDestination(String source, String destination){
		return flightDao.getFlightBySourceAndDestination(source, destination);
	}
	public List<Flight> getFlightsByAirline(String airline){
		return flightDao.getFlightByAirline(airline);
	}
	public Flight updateFlight(Integer id, Flight updatedFlight) {
	    Optional<Flight> opt = flightDao.getFlightById(id);
	    if (opt.isPresent()) {
	        Flight existingFlight = opt.get();
	        existingFlight.setAirline(updatedFlight.getAirline());
	        existingFlight.setArrivalTime(updatedFlight.getArrivalTime());
	        existingFlight.setDepartureTime(updatedFlight.getDepartureTime());
	        existingFlight.setDestination(updatedFlight.getDestination());
	        existingFlight.setPrice(updatedFlight.getPrice());
	        existingFlight.setSource(updatedFlight.getSource());
	        existingFlight.setTotalSeats(updatedFlight.getTotalSeats());
	        return flightDao.updateFlight(existingFlight);
	    }
	    throw new FlightNotFoundException("Flight not found with id " + id);
	}
	public void deleteFlight(Integer id) {
	    Optional<Flight> opt = flightDao.getFlightById(id);

	    if (opt.isPresent()) {
	        flightDao.deleteFlight(id);
	    } else {
	        throw new FlightNotFoundException("Flight not found with id " + id);
	    }
	}
	public Page<Flight> getFlightByPaginationAndSorting(Integer pageNumber, Integer pageSize,String field){
		Page<Flight> flights= flightDao.getFlightsByPageAndSort(pageNumber,pageSize,field);
		if(!flights.isEmpty()) {
			return flights;
		}
		throw new NoRecordException("No Records Found");
	}
	
}
