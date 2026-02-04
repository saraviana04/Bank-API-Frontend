package com.bankapi.controller;

import com.bankapi.dto.ApiMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public ApiMessage status() {
        return new ApiMessage("API bank-api está online.");
    }
}
