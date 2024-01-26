package com.customer.customerapplication.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

	@InjectMocks
	private JwtService jwtService;
	
	private String token;
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(jwtService, "expirationTimeInMs", 1000L);
	}

	/**
	 * Tests the generate token success scenario.
	 */
	@Test
	@Order(1)
	public void testGenerateToken() {
	    token = jwtService.generateToken("kani");
		assertNotNull(token);
	}
	
	
}
