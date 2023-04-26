package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/avg/{currency}/{date}")
    @ResponseStatus(value = OK)
    public ResponseEntity<?> GetAverageExchangeRate(
            @PathVariable(name = "currency") String currencyName,
            @PathVariable(name = "date") String date
    ) throws IOException, CurrencyNotFoundException {
        return ResponseEntity.status(OK).body(currencyService.GetAverageExchangeRate(currencyName, date));
    }

    @GetMapping("/minmax/{currency}/{days}")
    @ResponseStatus(value = OK)
    public ResponseEntity<?> GetMinMaxAverageValue(
            @PathVariable(name = "currency") String currencyName,
            @PathVariable(name = "days") Integer days
    ) throws IOException, CurrencyNotFoundException {
        if (days < 1 || days > 255) {
            return ResponseEntity.status(BAD_REQUEST).body("Invalid number of days (must be between 1 and 255).");
        }
        return ResponseEntity.status(OK).body(currencyService.GetMinMaxAverageValue(currencyName, days));
    }

    @GetMapping("/bidask/{currency}/{days}")
    @ResponseStatus(value = OK)
    public ResponseEntity<?> GetMajorDiffBuyAskRate(
            @PathVariable(name = "currency") String currencyName,
            @PathVariable(name = "days") Integer days
    ) throws IOException, CurrencyNotFoundException {
        if (days < 1 || days > 255) {
            return ResponseEntity.status(BAD_REQUEST).body("Invalid number of days (must be between 1 and 255).");
        }
        return ResponseEntity.status(OK).body(currencyService.GetMajorDiffBuyAskRate(currencyName, days));
    }

}
