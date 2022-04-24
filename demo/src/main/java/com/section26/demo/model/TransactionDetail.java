package com.section26.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("Transaction Detail")
public class TransactionDetail {
    @Id
    public String id;
    public String transaction_id;
    public String product_id;
    public Integer quantity;
}
