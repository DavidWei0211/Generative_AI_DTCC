package com.example.demo.service.implementation;

import com.example.demo.model.Client;
import com.example.demo.model.Transaction;
import com.example.demo.repo.ClientRepo;
import com.example.demo.repo.TransactionRepo;
import com.example.demo.service.AppService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AppServiceImpl implements AppService {

        private final ClientRepo clientRepo;
        private final TransactionRepo transactionRepo;

        @Override
        public Collection<Client> getAllClients() {
            log.info("getAllClients() called");
            return clientRepo.findAll();
        }

        @Override
        public Collection<Transaction> getAllTransactions() {
            log.info("getAllTransactions() called");
            return transactionRepo.findAll();
        }

        @Override
        public Collection<Transaction> getValidation() {
            Collection<Transaction> transactions = transactionRepo.findAll();
            Collection<Client> clients = clientRepo.findAll();

            LocalTime localTime = LocalTime.now();
            LocalDate localDate = LocalDate.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            //check validation of each transaction:
            // fromId, toId need to be different and from the list of clients,
            // amount needs to be positive,
            // price needs to be positive and less than 50,000,000
            //date must greater than current time
            //return the list of valid transactions

            Collection<Transaction> validTransactions = new ArrayList<>();
            for (Transaction transaction : transactions) {
                boolean isValid = true;
                if (transaction.getFromId() == transaction.getToId()
                        || transaction.getAmount() <= 0
                        || transaction.getPrice() <= 0
                        || transaction.getPrice() > 50000000
                        || transaction.getDate().compareTo(currentDate) < 0) {
                    isValid = false;
                }
                boolean isAssociatedWithClient = false;
                for (Client client : clients) {
                    if (transaction.getFromId() == client.getId() || transaction.getToId() == client.getId()) {
                        isAssociatedWithClient = true;
                        break;
                    }
                }
                if (isValid && isAssociatedWithClient) {
                    validTransactions.add(transaction);
                }
            }
            return validTransactions;
        }

    @Override
    public Collection<Transaction> matchingEngine() {
            //get the list of valid transactions
            Collection<Transaction> validTransactions = getValidation();
            //match the transactions with opposite fromId and toId and same date, save this pair as a matched transaction and randomly generate a trading id for it
            Collection<Transaction> matchedTransactions = new ArrayList<>();
            for (Transaction transaction : validTransactions) {
                for (Transaction transaction2 : validTransactions) {
                    if (transaction.getFromId() == transaction2.getToId()
                            && transaction.getToId() == transaction2.getFromId()
                            && transaction.getDate().compareTo(transaction2.getDate()) == 0) {
                        Transaction matchedTransaction = new Transaction();
                        matchedTransaction.setFromId(transaction.getFromId());
                        matchedTransaction.setToId(transaction.getToId());
                        matchedTransaction.setAmount(transaction.getAmount());
                        matchedTransaction.setPrice(transaction.getPrice());
                        matchedTransaction.setDate(transaction.getDate());
                        matchedTransaction.setTradeId("tradeId");
                        matchedTransactions.add(matchedTransaction);
                    }
                }
            }
            return matchedTransactions;



    }

}
