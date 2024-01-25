package com.customer.customerapplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.customer.customerapplication.entity.User;
import com.customer.customerapplication.repo.UserRepository;
import com.customer.customerapplication.requestpayload.AuthRequest;
/**
 * Service layer of login/signup user
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User save(AuthRequest userRequest) {
		Optional<User> user = userRepository.findByUserName(userRequest.getUsername());
		if (user.isEmpty()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = userRequest.getPassword();
			String encodedPassword = encoder.encode(rawPassword);
			User newUser = new User(userRequest.getUsername(), encodedPassword);
			newUser = userRepository.save(newUser);
			return newUser;
		} else {
			return user.get();
		}
	}
}
