package com.pvdong.lesson3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A horse with this name already exists!!!")
public class HorseAlreadyExistsException extends RuntimeException {
    public HorseAlreadyExistsException() {}
}
