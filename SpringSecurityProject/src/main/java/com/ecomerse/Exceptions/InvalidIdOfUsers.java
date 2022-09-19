package com.ecomerse.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdOfUsers extends RuntimeException {

	public InvalidIdOfUsers(String message) {
		super(message);
	}

}
