package com.customer.customerapplication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.customerapplication.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUserName(String username);

}
