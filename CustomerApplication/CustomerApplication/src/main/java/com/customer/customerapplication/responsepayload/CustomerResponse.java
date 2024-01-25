package com.customer.customerapplication.responsepayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for customer response. to avoid exposing the entity to the end user using this DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String dob;
	
}
