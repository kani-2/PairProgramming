package com.customer.customerapplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.customer.customerapplication.exception.CustomerNotFoundException;
import com.customer.customerapplication.requestpayload.CustomerRequest;
import com.customer.customerapplication.responsepayload.CustomerResponse;
import com.customer.customerapplication.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController controller;

	@Mock
	private CustomerService customerService;

	/**
	 * Tests the customer controller save functionality success scenario
	 */
	@Test
	public void testSave() {
		Mockito.when(customerService.save(any())).thenReturn(new CustomerResponse(1, "kani", "lastName", "1991-01-02"));
		ResponseEntity response = controller.saveCustomer(new CustomerRequest());
		assertNotNull(response);
		assertEquals("Customer saved successfully", response.getBody());
	}
	/**
	 * Tests the customer controller save functionality when the id not returned from DB failure scenario
	 */
	@Test
	public void testSave_EmptyId() {
		Mockito.when(customerService.save(any()))
				.thenReturn(new CustomerResponse(null, "kani", "lastName", "1991-01-02"));
		ResponseEntity response = controller.saveCustomer(new CustomerRequest());
		assertNotNull(response);
		assertEquals("Customer not saved", response.getBody());
	}
	/**
	 * Tests the customer controller get customer by id success scenario
	 */
	@Test
	public void testGetCustomerById() {
		Mockito.when(customerService.findById(any()))
				.thenReturn(new CustomerResponse(1, "kani", "lastName", "1991-01-02"));
		ResponseEntity response = controller.getCustomer(1);
		assertNotNull(response);
		assertEquals("kani", ((CustomerResponse) response.getBody()).getFirstName());
	}
	/**
	 * Tests the all customers fetch
	 */
	@Test
	public void testGetAllCustomers() {
		Mockito.when(customerService.findAll()).thenReturn(new ArrayList<>());
		ResponseEntity response = controller.getCustomers();
		assertNotNull(response);
	}
	/**
	 * Tests the delete functionality by passing valid id
	 */
	@Test
	public void testDeleteCustomerById() {
		Mockito.doNothing().when(customerService).deleteCustomerById(any());
		ResponseEntity response = controller.deleteCustomerById(1);
		assertNotNull(response);
		assertEquals("Customer deleted successfully", response.getBody());
	}
	/**
	 * Tests the delete functionality by passing the id where customer not there. Exception scenario
	 */
	@Test
	public void testDeleteByCustomer_ThrowException() {
		Mockito.doThrow(CustomerNotFoundException.class).when(customerService).deleteCustomerById(any());
		ResponseEntity response = controller.deleteCustomerById(1);
		assertNotNull(response);
		assertEquals("Customer not available for the given id", response.getBody());
	}
}
