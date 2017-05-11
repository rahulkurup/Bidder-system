package com.yieldlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "The response from bidders are bad. Cant calculate highest value")
public class InvalidResponseException extends RuntimeException {
}
