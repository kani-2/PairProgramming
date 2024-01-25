package com.customer.customerapplication.requestpayload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * THis DTO used for customer registration/retrieval.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
	@NotBlank(message="First name should not be empty")
	private String firstName;
	@NotBlank(message="Last name should not be empty")
	private String lastName;
	
	@NotBlank(message="Date of Birth should not be empty")
	private String dob;

}
