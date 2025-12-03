package com.flightbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Passenger;
import com.flightbooking.entity.Payment;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBooking();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/flight/{id}")
    public List<Booking> getBookingByFlightId(@PathVariable Integer id) {
        return bookingService.getBookingByFlightId(id);
    }

    @GetMapping("/date/{date}")
    public List<Booking> getBookingsByDate(@PathVariable LocalDateTime date) {
        return bookingService.getBookingsByDate(date);
    }

    @GetMapping("/status/{status}")
    public List<Booking> getBookingsByStatus(@PathVariable BookingStatus status) {
        return bookingService.getBookingByStatus(status);
    }

    @GetMapping("/{id}/passengers")
    public List<Passenger> getPassengersInABooking(@PathVariable Integer id) {
        return bookingService.getAllPassengers(id);
    }

    @PutMapping("/{id}/status/{status}")
    public Booking updateBookingStatus(@PathVariable Integer id, @PathVariable BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/{id}/payment")
    public Payment getPayment(@PathVariable Integer id) {
        return bookingService.getPayment(id);
    }

    @GetMapping("/pagination/{pageNumber}/{pageSize}/{field}")
    public Page<Booking> getBookingByPaginationAndSorting(
            @PathVariable Integer pageNumber,
            @PathVariable Integer pageSize,
            @PathVariable String field) {
        return bookingService.getBookingByPaginationAndSorting(pageNumber, pageSize, field);
    }
}
