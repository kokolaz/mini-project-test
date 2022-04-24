package com.section26.demo.controller;

import java.util.List;

import com.section26.demo.model.TransactionDetail;
import com.section26.demo.service.TransactionDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction_detail")
public class TransactionDetailController {
    @Autowired
    private TransactionDetailService transactionDetailService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDetail transactionDetail){
        TransactionDetail transactionDetailCreated = transactionDetailService.create(transactionDetail);
        return ResponseEntity.ok(transactionDetailCreated);
    }

    @GetMapping
    public ResponseEntity<?> listTransaction(){
        List<TransactionDetail> transactionsDetail = transactionDetailService.findAll();
        return ResponseEntity.ok(transactionsDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> transactionDetail(@PathVariable("id") String id){
        try{
            TransactionDetail transactionDetail = transactionDetailService.findById(id);
            return ResponseEntity.ok(transactionDetail);
        } catch (Exception e){
            InternalError internalError = new InternalError(e.getMessage());
            return ResponseEntity.internalServerError().body(internalError);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody TransactionDetail transactionDetail){
        try{
            TransactionDetail transactionDetailUpdated = transactionDetailService.update(id, transactionDetail);
            return ResponseEntity.ok(transactionDetailUpdated);
        } catch (Exception e){
            InternalError internalError = new InternalError(e.getMessage());
            return ResponseEntity.internalServerError().body(internalError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        try{
            transactionDetailService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            InternalError internalError = new InternalError(e.getMessage());
            return ResponseEntity.internalServerError().body(internalError);
        }
    }
}
