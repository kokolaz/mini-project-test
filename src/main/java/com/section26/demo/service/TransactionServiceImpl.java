package com.section26.demo.service;

import java.util.List;
import com.section26.demo.model.Transaction;
import com.section26.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(String id){
        return transactionRepository.findById(id).orElseThrow(() -> {
            throw new Error("Transaction with ID " + id + " is not found.");
        });
    }

    @Override
    public Transaction update(String id, Transaction transaction){
        Transaction transactionById = this.findById(id);
        transactionById.setCustomer_name(transaction.getCustomer_name());
        transactionById.setTransaction_details(transaction.getTransaction_details());
        transactionById.setIs_paid(transaction.getIs_paid());
        return transactionRepository.save(transactionById);
    }

    @Override
    public void delete(String id){
        Transaction transactionById = this.findById(id);
        transactionRepository.delete(transactionById);
    }
}
