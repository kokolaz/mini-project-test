package com.section26.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("Transaction")
public class Transaction {
    @Id
    public String id;
    public String customer_name;
    public Integer transaction_details;
    public String is_paid;
    public LocalDateTime created_at;
}
