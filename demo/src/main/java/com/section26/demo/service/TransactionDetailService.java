package com.section26.demo.service;

import java.util.List;

import com.section26.demo.model.TransactionDetail;

public interface TransactionDetailService {
    TransactionDetail create(TransactionDetail transactionDetail);
    List<TransactionDetail> findAll();
    TransactionDetail findById(String id);
    TransactionDetail update(String id, TransactionDetail transactionDetail);
    void delete(String id);
}
