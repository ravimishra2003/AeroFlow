package com.flightbooking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.entity.Flight;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.service.FlightService;


@RequestMapping("/booking")
@RestController
public class FlightController {
	@Autowired
	private FlightService flightService;
	@PostMapping
	public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
		Flight savedFlight= flightService.addFlight(flight);
		return new ResponseEntity<Flight>(savedFlight,HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<Flight>> getAllFlight() {
	    List<Flight> flights = flightService.getAllFlights();
	    if (!flights.isEmpty()) {
	        return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
	    }
	    return new ResponseEntity<List<Flight>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Flight> getFlightById(@PathVariable Integer id){
		Flight flight=flightService.getFlightById(id);
		if(flight!=null) {
			return new ResponseEntity<Flight>(flight,HttpStatus.OK);
		}
		return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/{source}/{destination}")
	public ResponseEntity<List<Flight>> getFlightBySourceAndDestination(@PathVariable String source,@PathVariable String destination){
	    List<Flight> flights = flightService.getFlightsBySourceAndDestination(source, destination);
	    if (!flights.isEmpty()) {
	        return new ResponseEntity<>(flights, HttpStatus.OK);
	    }
	    throw new FlightNotFoundException("No Flights Found");
	}
	@GetMapping("/airline/{airline}")
	public ResponseEntity<List<Flight>> getFlightByAirline(@PathVariable String airline){
	    List<Flight> flights = flightService.getFlightsByAirline(airline);
	    if (!flights.isEmpty()) {
	        return ResponseEntity.ok(flights);
	    }
	    throw new FlightNotFoundException("Airline not found");
	}
	@PutMapping("/{id}")
	public ResponseEntity<Flight> updateFlight(@PathVariable Integer id, @RequestBody Flight updatedFlight){
		Flight flight=flightService.updateFlight(id, updatedFlight);
		if(flight!=null) {
			return new ResponseEntity<Flight>(flight,HttpStatus.OK);
		}
		throw new FlightNotFoundException("invalid id flight not found");
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFlight(@PathVariable Integer id){
		flightService.deleteFlight(id);
		return ResponseEntity.ok("Flight deleted successfully");
	}
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<Page<Flight>> getFlightsByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize, @PathVariable String field){
		Page<Flight> flights=flightService.getFlightByPaginationAndSorting(pageNumber, pageSize, field);
		if(flights!=null) {
			return new ResponseEntity<Page<Flight>>(flights,HttpStatus.OK);
		}
		return new ResponseEntity<Page<Flight>>(HttpStatus.NOT_FOUND);
	}
}
