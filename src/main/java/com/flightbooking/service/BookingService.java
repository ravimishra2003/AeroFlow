package com.flightbooking.service;

import java.time.LocalDateTime;
import java.util.List;

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

    // CREATE BOOKING ==============================
    @Transactional
    public Booking createBooking(Booking booking) {

        // Validate passengers
        List<Passenger> passengers = booking.getPassengers();
        if (passengers == null || passengers.isEmpty()) {
            throw new PassengerNotFoundException("At least one passenger required");
        }
        passengers.forEach(p -> {
            if (p != null) p.setBooking(booking);
        });

        // Validate payment
        if (booking.getPayment() == null) {
            throw new PaymentNotFound("Payment information required");
        }
        booking.getPayment().setBooking(booking);

        // Validate flight
        if (booking.getFlight() == null || booking.getFlight().getId() == null) {
            throw new FlightNotFoundException("Flight id is required");
        }
        Flight found = flightRepo.findById(booking.getFlight().getId())
                .orElseThrow(() -> new FlightNotFoundException(
                        "Flight not found with id: " + booking.getFlight().getId()));

        // Check seat availability
        int passengerCount = passengers.size();
        int availableSeats = found.getTotalSeats();

        if (passengerCount > availableSeats) {
            throw new SeatNotAvailable("Not enough seats available");
        }

        // Price calculation
        double totalAmount = found.getPrice() * passengerCount;

        found.setTotalSeats(availableSeats - passengerCount);
        booking.setFlight(found);
        booking.getPayment().setAmount(totalAmount);

        return bookingDao.createBooking(booking);
    }

    // READ ALL ======================================
    public List<Booking> getAllBooking() {
        return bookingDao.getAllBooking(); // return [] if none
    }

    // READ BY ID =====================================
    public Booking getBookingById(Integer id) {
        return bookingDao.getBookingById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + id));
    }

    // READ BY FLIGHT ==================================
    public List<Booking> getBookingByFlightId(Integer id) {
        return bookingDao.getBookingByFlightId(id);
    }

    // READ BY DATE ====================================
    public List<Booking> getBookingsByDate(LocalDateTime date) {
        return bookingDao.getBookingByDate(date);
    }

    // READ BY STATUS ==================================
    public List<Booking> getBookingByStatus(BookingStatus status) {
        return bookingDao.getBookingByStatus(status);
    }

    // PASSENGERS ======================================
    public List<Passenger> getAllPassengers(Integer id) {
        Booking booking = getBookingById(id);
        return booking.getPassengers();
    }

    // PAYMENT ==========================================
    public Payment getPayment(Integer id) {
        Booking booking = getBookingById(id);
        if (booking.getPayment() == null) {
            throw new PaymentNotFound("No payment details found for booking id " + id);
        }
        return booking.getPayment();
    }

    // UPDATE STATUS ====================================
    public Booking updateBookingStatus(Integer id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Booking not found with id " + id));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    // DELETE BOOKING ===================================
    public void deleteBooking(Integer id) {
        if (!bookingDao.existsBooking(id)) {
            throw new BookingNotFoundException("Booking not found with id " + id);
        }
        bookingDao.deleteBooking(id);
    }

    // PAGINATION + SORT ==============================
    public Page<Booking> getBookingByPaginationAndSorting(Integer pageNumber, Integer pageSize, String field) {
        return bookingDao.getBookingByPageAndSort(pageNumber, pageSize, field);
    }
}
