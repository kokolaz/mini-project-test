package com.alterra.demo.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseDao {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "is_deleted")
    private Boolean isdeleted;

    @PrePersist
    void onCreated(){
        this.createdAt = LocalDateTime.now();
        this.createdBy = "kokolaz";
        this.isdeleted = Boolean.FALSE;
    }

    @PreUpdate
    void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }
}
