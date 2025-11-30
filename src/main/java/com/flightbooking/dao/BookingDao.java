package com.flightbooking.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Passenger;
import com.flightbooking.entity.Payment;
import com.flightbooking.enums.BookingStatus;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.repository.PassengerRepository;
import com.flightbooking.repository.PaymentRepository;

@Repository
public class BookingDao {
    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private PassengerRepository passengerRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    public boolean existsBooking(Integer id) {
        return bookingRepo.existsById(id);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBooking() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepo.findById(id);
    }

    public List<Booking> getBookingByFlightId(Integer id) {
        return bookingRepo.findByFlightId(id);
    }

    public List<Booking> getBookingByStatus(BookingStatus status) {
        return bookingRepo.findByStatus(status);
    }
    

    public void deleteBooking(Integer id) {
        bookingRepo.deleteById(id);
    }

    public Page<Booking> getBookingByPageAndSort(int pageNumber, int pageSize, String field) {
        return bookingRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).descending()));
    }

    public List<Booking> getBookingByDate(LocalDateTime date) {
        return bookingRepo.findByBookingDate(date);
    }
    public Optional<Payment> findPaymentByBookingId(Integer id) {
        return paymentRepo.findByBookingId(id);
    }

    public List<Passenger> getPassengers(Integer bookingId) {
        return passengerRepo.findByBookingId(bookingId);
    }
    
}