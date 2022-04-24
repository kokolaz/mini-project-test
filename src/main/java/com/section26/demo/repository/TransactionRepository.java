package com.section26.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.section26.demo.model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
    
}
