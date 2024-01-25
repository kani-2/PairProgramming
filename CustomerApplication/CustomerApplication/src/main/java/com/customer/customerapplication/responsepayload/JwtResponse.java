package com.customer.customerapplication.responsepayload;

import java.io.Serializable;
/**
 * JWT response DTO used for JWT token generation.
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}