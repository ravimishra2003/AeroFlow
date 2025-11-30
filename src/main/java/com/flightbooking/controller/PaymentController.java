package com.flightbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.entity.Payment;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.service.PaymentService;
@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentservice;
	@PostMapping
	public ResponseEntity<Payment> recordPayment(@RequestBody Payment payment) {
		Payment savedPayment=paymentservice.recordPayment(payment);
		return new ResponseEntity<Payment>(savedPayment,HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayment(){
		List<Payment> payments=paymentservice.getAllPayment();
		return new ResponseEntity<List<Payment>>(payments,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id){
		Payment payment=paymentservice.getPaymentById(id);
		if(payment!=null) {
			return new ResponseEntity<Payment>(payment,HttpStatus.OK);
		}
		return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Payment>> getPaymentByStatus(@PathVariable String status){
		List<Payment> payments=paymentservice.getPaymentByStatus(status);
		if(payments!=null) {
			return new ResponseEntity<List<Payment>>(payments,HttpStatus.OK);
		}
		return new ResponseEntity<List<Payment>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/amount/{amount}")
	public ResponseEntity<List<Payment>> getPaymentGreaterThan(@PathVariable double amount){
		List<Payment> payments=paymentservice.getPaymentGreaterThan(amount);
		if(payments!=null) {
			return new ResponseEntity<List<Payment>>(payments,HttpStatus.OK);
		}
		return new ResponseEntity<List<Payment>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/mode/{mode}")
	public ResponseEntity<List<Payment>> getPaymentByMode(@PathVariable String mode){
		List<Payment> payments=paymentservice.getPaymentByModeOfTransaction(mode);
		if(payments!=null) {
			return new ResponseEntity<List<Payment>>(payments,HttpStatus.OK);
		}
		return new ResponseEntity<List<Payment>>(HttpStatus.NOT_FOUND);
	}
	@PutMapping("/id")
	public ResponseEntity<Payment> updatePayment(@PathVariable Integer id, @RequestBody Payment updatedPayment){
		Payment payment=paymentservice.updatePayment(id, updatedPayment);
		if(payment!=null) {
			return new ResponseEntity<Payment>(payment,HttpStatus.OK);
		}
		throw new FlightNotFoundException("invalid id flight not found");
	}
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<Page<Payment>> getPaymentByPageAndSort(@PathVariable Integer pageNumber,@PathVariable Integer pageSize, @PathVariable String field){
		Page<Payment> payments=paymentservice.getpaymentByPageAndSort(pageNumber, pageSize, field);
		if(payments!=null) {
			return new ResponseEntity<Page<Payment>>(payments,HttpStatus.OK);
		}
		return new ResponseEntity<Page<Payment>>(HttpStatus.NOT_FOUND);
	}
}
