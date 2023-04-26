package com.example.demo;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException() {
        super("Currency with given code does not exist.");
    }
}
