package com.customer.customerapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customer.customerapplication.entity.User;
import com.customer.customerapplication.repo.UserRepository;
import com.customer.customerapplication.requestpayload.AuthRequest;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;
	/**
	 * Tests the save user functionality when user is present in DB
	 */
	@Test
	public void testUserSave() {
		Mockito.when(repository.findByUserName(any())).thenReturn(Optional.of(new User(1,"kani","password")));
		User user= service.save(new AuthRequest("kani", "password"));
		assertNotNull(user);
	}
	/**
	 * Tests the save user functionality when user name is not present in DB
	 */
	@Test
	public void testUserSave_whenUserNameNotPresent() {
		Mockito.when(repository.findByUserName(any())).thenReturn(Optional.empty());
		Mockito.when(repository.save(any())).thenReturn(new User(1,"kani","encodedpassword"));
		User user= service.save(new AuthRequest("kani", "password"));
		assertNotNull(user);
		assertEquals("kani", user.getUserName());
	}
}
