package com.customer.customerapplication.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private LocalDate dob;
	
	public Customer(String firstName, String lastName, LocalDate dob) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.dob=dob;
	}
}
