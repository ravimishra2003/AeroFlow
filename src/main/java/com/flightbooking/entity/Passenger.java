package com.flightbooking.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightbooking.enums.PassengerGender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int age;
	@Enumerated(EnumType.STRING)
	private PassengerGender gender;
	private long contactNumber;
	private int seatNumber;
	@JsonIgnore
	@JoinColumn
	@ManyToOne
	private Booking booking;
}
