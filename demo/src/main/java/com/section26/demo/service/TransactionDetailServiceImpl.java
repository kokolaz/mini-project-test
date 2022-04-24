package com.section26.demo.service;

import java.util.List;
import com.section26.demo.model.TransactionDetail;
import com.section26.demo.repository.TransactionDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {
    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public TransactionDetail create(TransactionDetail transactionDetail){
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public List<TransactionDetail> findAll(){
        return transactionDetailRepository.findAll();
    }

    @Override
    public TransactionDetail findById(String id){
        return transactionDetailRepository.findById(id).orElseThrow(() -> {
            throw new Error("Transaction with ID " + id + " is not found.");
        });
    }

    @Override
    public TransactionDetail update(String id, TransactionDetail transactionDetail){
        TransactionDetail transactionDetailById = this.findById(id);
        transactionDetailById.setTransaction_id(transactionDetail.getTransaction_id());
        transactionDetailById.setProduct_id(transactionDetail.getProduct_id());
        transactionDetailById.setQuantity(transactionDetail.getQuantity());
        return transactionDetailRepository.save(transactionDetailById);
    }

    @Override
    public void delete(String id){
        TransactionDetail transactionDetailById = this.findById(id);
        transactionDetailRepository.delete(transactionDetailById);
    }
}
