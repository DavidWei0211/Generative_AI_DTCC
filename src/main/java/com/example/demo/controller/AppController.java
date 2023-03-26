package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Response;
import com.example.demo.model.Transaction;
import com.example.demo.service.AppService;
import com.example.demo.service.implementation.AppServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppController {
    private final AppServiceImpl appService;
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return (List<Client>) appService.getAllClients();
    }
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) appService.getAllTransactions();
    }
    @GetMapping("/validation")
    public List<Transaction> getValidation() {
        return (List<Transaction>) appService.getValidation();
    }

    @GetMapping("/countValidation")
    public long getCountValidation() {
        return appService.getValidation().size();
    }
}

