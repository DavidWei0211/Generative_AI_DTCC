package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Transaction;

import java.util.Collection;

public interface AppService {

    // Client
    Collection<Client> getAllClients();

    // Transaction
    Collection<Transaction> getAllTransactions();

    Collection<Transaction> getValidation();

    Collection<Transaction> matching();

    Collection<Transaction> matchingEngine();
}
