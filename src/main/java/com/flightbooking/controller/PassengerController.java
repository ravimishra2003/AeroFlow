package com.flightbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.entity.Passenger;
import com.flightbooking.exception.PassengerNotFoundException;
import com.flightbooking.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
	@Autowired
	private PassengerService passengerservice;
	@PostMapping
	public ResponseEntity<Passenger> addPassenger(@RequestBody Passenger passenger){
		Passenger savePassenger=passengerservice.addPassenger(passenger);
		return new ResponseEntity<Passenger>(savePassenger,HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<Passenger>> getAllPassenger(){
		List<Passenger> passengers=passengerservice.getAllPassenger();
		if(!passengers.isEmpty()) {
			return new ResponseEntity<List<Passenger>>(passengers,HttpStatus.OK);
		}
		return new ResponseEntity<List<Passenger>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Integer id){
		Passenger passenger=passengerservice.getPassengerById(id);
		if(passenger!=null) {
			return new ResponseEntity<Passenger>(passenger,HttpStatus.OK);
		}
		return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Passenger> updatePassenger(@PathVariable Integer id, @RequestBody Passenger updatedPassenger){
		Passenger passenger =passengerservice.updatePassenger(id, updatedPassenger);
		if(passenger!=null) {
			return new ResponseEntity<Passenger>(passenger,HttpStatus.OK);
		}
		return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/contactNumber/{contactNumber}")
	public ResponseEntity<Passenger> getPassengerByContactNumber(@PathVariable Long contactNumber){
		Optional<Passenger> passenger=passengerservice.getPassengerByContactNumber(contactNumber);
		if(!passenger.isEmpty()) {
			return new ResponseEntity<Passenger>(passenger.get(),HttpStatus.OK);
		}
		throw new PassengerNotFoundException("Invalid contact number");
	}
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<Page<Passenger>> getPassengerByPageAndSort(@PathVariable Integer pageNumber,@PathVariable Integer pageSize, @PathVariable String field){
		Page<Passenger> passengers=passengerservice.getPassengerByPageAndSort(pageNumber, pageSize, field);
		if(!passengers.isEmpty()) {
			return new ResponseEntity<Page<Passenger>>(passengers,HttpStatus.OK);
		}
		return new ResponseEntity<Page<Passenger>>(HttpStatus.NOT_FOUND);
	}
	 
}
