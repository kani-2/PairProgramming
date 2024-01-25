package com.customer.customerapplication.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customerapplication.entity.User;
import com.customer.customerapplication.requestpayload.AuthRequest;
import com.customer.customerapplication.responsepayload.JwtResponse;
import com.customer.customerapplication.service.JwtService;
import com.customer.customerapplication.service.UserService;

/**
 * User controller mainly focusing on creating new user in db with password and
 * login functionality for the registered user.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
			throws Exception {
		logger.info("User requested for login");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		User user = userService.save(authenticationRequest);
		final String token = jwtService.generateToken(user.getUserName());
		logger.info("User authenticated successfully");
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(@RequestBody AuthRequest authenticationRequest) throws Exception {
		logger.info("User sign up process started");
		User user = userService.save(authenticationRequest);
		logger.info("User saved successfully in database");
		return ResponseEntity.ok(user.getUserName() + " user saved successfully, you can login uing the password");
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
