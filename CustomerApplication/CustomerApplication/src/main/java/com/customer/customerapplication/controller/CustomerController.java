package com.customer.customerapplication.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customerapplication.exception.CustomerNotFoundException;
import com.customer.customerapplication.requestpayload.CustomerRequest;
import com.customer.customerapplication.responsepayload.CustomerResponse;
import com.customer.customerapplication.responsepayload.ErrorResponse;
import com.customer.customerapplication.service.CustomerService;

import jakarta.validation.Valid;

/**
 * Customer Rest controller which has customer save, get by id, delete, delete
 * by id and get customers list functionality
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	CustomerService customerService;

	@PostMapping("/save")
	public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerRequest customer) {
		logger.info("User want to save the customer details");
		CustomerResponse response = customerService.save(customer);

		if (response.getId() != null) {
			return ResponseEntity.ok("Customer saved successfully");
		} else {
			return ResponseEntity.badRequest().body("Customer not saved");
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable Integer id) {
		logger.info("User want to get the customer by id:" + id);
		CustomerResponse response = customerService.findById(id);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getCustomers() {
		logger.info("Get all customers from DB");
		List<CustomerResponse> response = customerService.findAll();
		logger.info("All the customer details fetched successfully");
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id) {
		logger.info("Delete customer from DB by id " + id);
		try {
			customerService.deleteCustomerById(id);
		} catch (CustomerNotFoundException exception) {
			return ResponseEntity.ok().body("Customer not available for the given id");
		}
		logger.info("Customer deleted successfully");
		return ResponseEntity.ok().body("Customer deleted successfully");
	}

	@ExceptionHandler(value = { CustomerNotFoundException.class, SQLException.class })
	public ResponseEntity<?> handleException(Exception exception) {
		if (exception instanceof CustomerNotFoundException) {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
		} else {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
		}
	}
}
