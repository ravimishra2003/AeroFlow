package com.flightbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flightbooking.dao.PaymentDao;
import com.flightbooking.entity.Payment;
import com.flightbooking.exception.IdNotFoundException;
import com.flightbooking.exception.NoRecordException;

@Service
public class PaymentService {
	@Autowired
	private PaymentDao paymentdao;
	public Payment recordPayment(Payment payment){
		return paymentdao.recordPayment(payment);
	}
	public List<Payment> getAllPayment(){
		return paymentdao.getAllPayment();
	}
	public Payment getPaymentById(Integer id) {
		Optional<Payment> opt=paymentdao.getPaymentById(id);
		if(!opt.isEmpty()) {
			return opt.get();
		}
		throw new IdNotFoundException("Id is invalid");
	}
	public List<Payment> getPaymentByStatus(String status){
		return paymentdao.getpaymentByStatus(status);
	}
	public List<Payment> getPaymentGreaterThan(double amount){
		return paymentdao.getPaymentGreaterThan(amount);
	}
	public List<Payment> getPaymentByModeOfTransaction(String mode){
		return paymentdao.getPaymentByModeOfTransaction(mode);
	}
	public Payment updatePayment(Integer id, Payment updatedPayment) {
	    Optional<Payment> optionalPayment = paymentdao.getPaymentById(id);
	    if (optionalPayment.isPresent()) {
	        Payment existingPayment = optionalPayment.get();
	        existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
	        existingPayment.setAmount(updatedPayment.getAmount());
	        existingPayment.setMode(updatedPayment.getMode());
	        existingPayment.setStatus(updatedPayment.getStatus());
	        return paymentdao.updatePayment(existingPayment);
	    }
	    throw new IdNotFoundException("Payment not found with id " + id);
	}
	public Page<Payment> getpaymentByPageAndSort(int pageNumber,int pageSize,String field){
		Page<Payment> payments=paymentdao.getPaymentByPageAndSort(pageNumber, pageSize, field);
		if(!payments.isEmpty()) {
			return payments;
		}
		throw new NoRecordException("No records found");
	}
	
}
