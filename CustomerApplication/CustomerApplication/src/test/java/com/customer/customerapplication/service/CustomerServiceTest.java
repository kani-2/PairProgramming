package com.customer.customerapplication.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customer.customerapplication.entity.Customer;
import com.customer.customerapplication.exception.CustomerNotFoundException;
import com.customer.customerapplication.repo.CustomerRepository;
import com.customer.customerapplication.requestpayload.CustomerRequest;
import com.customer.customerapplication.responsepayload.CustomerResponse;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	public void testSave() {
		Mockito.when(customerRepository.save(any())).thenReturn(
				new Customer(1, "kani", "sundar", LocalDate.parse("1991-09-01", DateTimeFormatter.ISO_DATE)));
		CustomerRequest customerRequest = new CustomerRequest("kani", "sundar", "1991-09-01");
		CustomerResponse customerResponse = customerService.save(customerRequest);
		assertNotNull(customerResponse);
	}

	/**
	 * Tests the get customer by id success scenario
	 */
	@Test
	public void testGetCustomerById() {
		Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(Mockito.mock(Customer.class)));
		CustomerResponse response = customerService.findById(1);
		assertNotNull(response);
	}

	/**
	 * Tests the all customers fetch
	 */
	@Test
	public void testGetAllCustomers() {
		Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
		List<CustomerResponse> response = customerService.findAll();
		assertNotNull(response);
	}

	/**
	 * Tests the delete functionality by passing valid id
	 */
	@Test
	public void testDeleteCustomerById() {
		Mockito.doNothing().when(customerRepository).deleteById(any());
		Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(Mockito.mock(Customer.class)));
		customerService.deleteCustomerById(1);
		Mockito.verify(customerRepository, atLeastOnce()).findById(1);
		Mockito.verify(customerRepository, atLeastOnce()).deleteById(1);
	}

	/**
	 * Tests the delete functionality by passing the id where customer not there.
	 * Exception scenario
	 */
	@Test
	public void testDeleteByCustomer_ThrowException() {
		Mockito.doThrow(CustomerNotFoundException.class).when(customerRepository).findById(any());
		assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomerById(1));
	}
}
