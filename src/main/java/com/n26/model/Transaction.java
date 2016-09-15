package com.n26.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratyushverma on 9/15/16.
 */
public class Transaction {
    @NotNull
    private Double amount;
    @NotNull
    private String type;

    @JsonProperty("parent_id")
    private Long parentId;
    private List<Long> childTransactions = new ArrayList<>();
    private Long transactionId;

    public Transaction() {
    }

    public Transaction(Double amount, String type, Long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    @JsonIgnore
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @JsonIgnore
    public List<Long> getChildTransactions() {
        return childTransactions;
    }

    public void setChildTransactions(List<Long> childTransactions) {
        this.childTransactions = childTransactions;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
