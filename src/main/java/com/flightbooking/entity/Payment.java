package com.flightbooking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightbooking.enums.PaymentMode;
import com.flightbooking.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDateTime paymentDate;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private PaymentMode mode;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Booking booking;
}
