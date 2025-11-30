package com.flightbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flightbooking.dao.PassengerDao;
import com.flightbooking.entity.Passenger;
import com.flightbooking.exception.IdNotFoundException;
import com.flightbooking.exception.NoRecordException;

@Service
public class PassengerService {
	@Autowired
	private PassengerDao passengerDao;
	public Passenger addPassenger(Passenger passenger) {
		return passengerDao.addPassenger(passenger);
	}
	public List<Passenger> getAllPassenger(){
		return passengerDao.getAllPassenger();
	}
	public Passenger getPassengerById(Integer id) {
		Optional<Passenger> opt=passengerDao.getPassengerById(id);
		if(!opt.isEmpty()) {
			return opt.get();
		}
		throw new IdNotFoundException("Id not found");
	}
	public Passenger updatePassenger(Integer id, Passenger updatedPassenger) {
	    Optional<Passenger> opt = passengerDao.getPassengerById(id);
	    if (opt.isPresent()) {
	        Passenger existingPassenger = opt.get();
	        existingPassenger.setName(updatedPassenger.getName());
	        existingPassenger.setAge(updatedPassenger.getAge());
	        existingPassenger.setGender(updatedPassenger.getGender());
	        existingPassenger.setSeatNumber(updatedPassenger.getSeatNumber());
	        existingPassenger.setContactNumber(updatedPassenger.getContactNumber());
	        return passengerDao.updatePassenger(existingPassenger);
	    }
	    throw new IdNotFoundException("Passenger not found with id " + id);
	}
	public Optional<Passenger> getPassengerByContactNumber(Long contactNumber) {
		return passengerDao.getPassengerByContact(contactNumber);
	}
	public Page<Passenger> getPassengerByPageAndSort(Integer pageNumber,Integer pageSize, String field){
		Page<Passenger> passengers=passengerDao.getPassengerByPageAndSort(pageNumber, pageSize, field);
		if(!passengers.isEmpty()) {
			return passengers;
		}
		throw new NoRecordException("No records found");
	}

}
