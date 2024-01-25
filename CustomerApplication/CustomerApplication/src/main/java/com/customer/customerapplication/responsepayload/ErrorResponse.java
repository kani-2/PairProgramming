package com.customer.customerapplication.responsepayload;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * When the exception thrown the error response class will be used to create response entity.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
	private int statusCode;
	private String message;
}
