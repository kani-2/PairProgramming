package com.customer.customerapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.customerapplication.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}
