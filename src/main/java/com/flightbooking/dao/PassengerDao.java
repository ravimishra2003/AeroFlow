package com.flightbooking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Passenger;
import com.flightbooking.repository.PassengerRepository;

@Repository
public class PassengerDao {
	@Autowired
	private PassengerRepository passengerRepo;
	public Passenger addPassenger(Passenger passenger) {
		return passengerRepo.save(passenger);
	}
	public List<Passenger> getAllPassenger(){
		return passengerRepo.findAll();
	}
	public Optional<Passenger> getPassengerById(Integer id){
		return passengerRepo.findById(id);
	}
	public Passenger updatePassenger(Passenger passenger) {
		return passengerRepo.save(passenger);
	}
	public Optional<Passenger> getPassengerByContact(Long contactNumber) {
		return passengerRepo.findBookingByContactNumber(contactNumber);
	}
	public Page<Passenger> getPassengerByPageAndSort(int pageNumber, int pageSize,String field){
		return passengerRepo.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).descending()));
	}

	
}
