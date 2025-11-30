package com.flightbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	List<Payment> findByStatus(String status);
	List<Payment> findByAmountGreaterThan(double amount);
	List<Payment> findByMode(String mode);
	Optional<Payment> findByBookingId(Integer bookingId);
}
