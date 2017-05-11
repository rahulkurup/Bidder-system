package com.yieldlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "The request is bad. Request must have id and attributes")
public class InvalidInputException extends RuntimeException {
}
