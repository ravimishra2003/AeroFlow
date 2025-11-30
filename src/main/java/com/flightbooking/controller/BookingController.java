package com.flightbooking.controller;

import java.time.LocalDateTime;
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

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Passenger;
import com.flightbooking.entity.Payment;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.exception.BookingNotFoundException;
import com.flightbooking.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBooking();
        if(!bookings.isEmpty()) {
        	return new ResponseEntity<List<Booking>>(bookings,HttpStatus.OK);
        }
        return new ResponseEntity<List<Booking>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
    	Booking booking=bookingService.getBoookingById(id);
    	if(booking!=null) {
    		return new ResponseEntity<Booking>(booking,HttpStatus.OK);
    	}
    	return new ResponseEntity<Booking>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<List<Booking>> getBookingByFlightId(@PathVariable Integer id) {
        List<Booking> bookings = bookingService.getBookingByFlightId(id);
        if (!bookings.isEmpty()) return ResponseEntity.ok(bookings);
        throw new BookingNotFoundException("No bookings found");
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable LocalDateTime date) {
    	List<Booking> bookings = bookingService.getBookingsByDate(date);
    	return new ResponseEntity<List<Booking>>(bookings,HttpStatus.OK);
    	
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        List<Booking> bookings = bookingService.getBookingByStatus(status);
        if (!bookings.isEmpty()) {
        	return new ResponseEntity<List<Booking>>(bookings,HttpStatus.OK);
        }
        throw new BookingNotFoundException("No bookings found");
    }
    
    @GetMapping("/{id}/passengers")
    public ResponseEntity<List<Passenger>> getPassengersInABooking(@PathVariable Integer id) {
        List<Passenger> passengers=bookingService.getAllPassengers(id);
        return new ResponseEntity<List<Passenger>>(passengers,HttpStatus.OK);
    }
    
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Integer id, @PathVariable BookingStatus status){
    	Booking book=bookingService.updateBookingStatus(id, status);
    	return new ResponseEntity<Booking>(book,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Deleted");
    }  

    @GetMapping("/{id}/payment")
    public ResponseEntity<Payment> getPayment(@PathVariable Integer id) {
    	
        return ResponseEntity.ok(bookingService.getPayment(id));
    }

    @GetMapping("/pagination/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<Page<Booking>> getBookingByPaginationAndSort(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field){
        Page<Booking> bookings = bookingService.getBookingByPaginationAndSorting(pageNumber, pageSize, field);
        if(!bookings.isEmpty()) {
        	return new ResponseEntity<Page<Booking>>(bookings,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
