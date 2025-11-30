package com.flightbooking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Payment;
import com.flightbooking.repository.PaymentRepository;

@Repository
public class PaymentDao {
	@Autowired
	private PaymentRepository paymentRepo;
	public Payment recordPayment(Payment payment) {
		return paymentRepo.save(payment);
	}
	public List<Payment> getAllPayment(){
		return paymentRepo.findAll();
	}
	public Optional<Payment> getPaymentById(Integer id) {
		return paymentRepo.findById(id);
	}
	public List<Payment> getpaymentByStatus(String status){
		return paymentRepo.findByStatus(status);
	}
	public List<Payment> getPaymentGreaterThan(double amount){
		return paymentRepo.findByAmountGreaterThan(amount);
	}
	public List<Payment> getPaymentByModeOfTransaction(String mode){
		return paymentRepo.findByMode(mode);
	}
	public Payment updatePayment(Payment payment) {
		return paymentRepo.save(payment);
	}
	public Page<Payment> getPaymentByPageAndSort(Integer pageNumber, Integer pageSize, String field){
		return paymentRepo.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).descending()));
	}
	
}
