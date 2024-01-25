package com.customer.customerapplication.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customerapplication.entity.Customer;
import com.customer.customerapplication.exception.CustomerNotFoundException;
import com.customer.customerapplication.repo.CustomerRepository;
import com.customer.customerapplication.requestpayload.CustomerRequest;
import com.customer.customerapplication.responsepayload.CustomerResponse;
/**
 * Service layer of customer functionalities
 */
@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	@Autowired
	CustomerRepository customerRepository;

	public CustomerResponse save(CustomerRequest request) {
		logger.info("Converting dob string type to Date");
		LocalDate date = LocalDate.parse(request.getDob(), DateTimeFormatter.ISO_DATE);
		logger.info("converted dob string type to Date");
		Customer customer = new Customer(request.getFirstName(), request.getLastName(), date);
		logger.info("Going to call the customer repo to save the details");
		customer = customerRepository.save(customer);
		logger.info("Customer saved successfully");
		return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getDob() != null ? customer.getDob().toString() : null);
	}

	public CustomerResponse findById(Integer id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not in DB for the id"));
		return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getDob() != null ? customer.getDob().toString() : null);
	}

	public List<CustomerResponse> findAll() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map(customer -> new CustomerResponse(customer.getId(), customer.getFirstName(),
				customer.getLastName(), customer.getDob() != null ? customer.getDob().toString() : null)).collect(Collectors.toList());
	}

	public void deleteCustomerById(Integer id) {
		customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not in DB for the id"));
		customerRepository.deleteById(id);
	}
}
