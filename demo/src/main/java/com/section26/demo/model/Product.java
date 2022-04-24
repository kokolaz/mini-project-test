package com.section26.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Products")
public class Product {
    @Id
    public String id;
    public String name;
    public Float price;
}
