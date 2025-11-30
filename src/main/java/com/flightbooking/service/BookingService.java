package com.flightbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flightbooking.dao.BookingDao;
import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Passenger;
import com.flightbooking.entity.Payment;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.exception.BookingNotFoundException;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.exception.IdNotFoundException;
import com.flightbooking.exception.NoRecordException;
import com.flightbooking.exception.PassengerNotFoundException;
import com.flightbooking.exception.PaymentNotFound;
import com.flightbooking.exception.SeatNotAvailable;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.repository.FlightRepository;

import jakarta.transaction.Transactional;
@Service
public class BookingService {
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private FlightRepository flightRepo;
    @Autowired
    private BookingRepository bookingRepository;
    
    @Transactional
    public Booking createBooking(Booking booking) {
        List<Passenger> passengers = booking.getPassengers();
        if (passengers == null || passengers.isEmpty()) {
            throw new PassengerNotFoundException("At least one passenger required");
        }
        for (Passenger p : passengers) {
            if (p != null) {
            	p.setBooking(booking);
            }
        }
        if (booking.getPayment() == null) {
            throw new PaymentNotFound("Payment information required");
        }
        booking.getPayment().setBooking(booking);

        if (booking.getFlight() == null || booking.getFlight().getId() == null) {
            throw new FlightNotFoundException("Flight id is required");
        }
        Flight found = flightRepo.findById(booking.getFlight().getId())
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + booking.getFlight().getId()));
        
        int passengerCount = passengers.size();
        int availableSeat = found.getTotalSeats();

        if (passengerCount > availableSeat) {
            throw new SeatNotAvailable("Not enough seats available");
        }
        double flightPrice = found.getPrice();
        double totalAmount = flightPrice * passengerCount;
        found.setTotalSeats(availableSeat - passengerCount);
        booking.setFlight(found);
        booking.getPayment().setAmount(totalAmount);
        return bookingDao.createBooking(booking);
    }

    public List<Booking> getAllBooking() {
        return bookingDao.getAllBooking();
    }

    public Booking getBoookingById(Integer id) {
        Optional<Booking> booking=bookingDao.getBookingById(id);
        if(booking.isPresent()) {
        	return booking.get();
        }
        else {
        	throw new BookingNotFoundException("Booking not found with id "+id);
        }
        
    }

    public List<Booking> getBookingByFlightId(Integer id) {
        return bookingDao.getBookingByFlightId(id);
    }
    
    public List<Booking> getBookingsByDate(LocalDateTime date) {
        List<Booking> bookings=bookingDao.getBookingByDate(date);
        if(!bookings.isEmpty()) {
     	   return bookings;
        }
        throw new NoRecordException("No Record Found for date "+date);
     }
    
    public List<Booking> getBookingByStatus(BookingStatus status) {
    	List<Booking> bookings=bookingDao.getBookingByStatus(status);
    	if(!bookings.isEmpty()) {
    		return bookings;
    	}
    	throw new BookingNotFoundException("Booking not found");
        
    }
    
    public List<Passenger> getAllPassengers(Integer id) {
		Optional<Booking> booking=bookingDao.getBookingById(id);
		if(booking.isPresent()) {
			return booking.get().getPassengers();
		}
		throw new BookingNotFoundException("booking not found");
	}
    public Payment getPayment(Integer id) {
		Optional<Booking> booking = bookingDao.getBookingById(id);
		if(booking.isPresent()) {
			return booking.get().getPayment();
		}
		throw new BookingNotFoundException("No Payment details found");
	}
    
    public Booking updateBookingStatus(Integer id, BookingStatus statusStr) {
    	Optional<Booking> opt=bookingRepository.findById(id);
    	if(opt.isPresent()) {
    		Booking book=opt.get();
    		book.setStatus(statusStr);
    		return bookingRepository.save(book);
    	}
    	else {
    		throw new IdNotFoundException("Booking not found with id "+id);
    	}
    }
    public void deleteBooking(Integer id) {
        if (!bookingDao.existsBooking(id)) {
            throw new BookingNotFoundException("Booking not found");
        }
        bookingDao.deleteBooking(id);
    }

    public Page<Booking> getBookingByPaginationAndSorting(Integer pageNumber, Integer pageSize, String field) {
        Page<Booking> bookings = bookingDao.getBookingByPageAndSort(pageNumber, pageSize, field);
        if (!bookings.isEmpty()) {
            return bookings;
        }
        throw new NoRecordException("No Records Found");
    }
}

