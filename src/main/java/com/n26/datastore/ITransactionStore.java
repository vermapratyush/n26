package com.n26.datastore;

import com.n26.exception.NoParentTransactionFound;
import com.n26.exception.NoTransactionFound;
import com.n26.model.Transaction;

import java.util.List;

/**
 * Created by pratyushverma on 9/15/16.
 */
public interface ITransactionStore {
    Transaction getTransaction(Long transactionId) throws NoTransactionFound;

    List<Transaction> getAllTransaction(Long transactionId) throws NoTransactionFound;

    boolean putTransaction(Transaction transaction) throws NoParentTransactionFound;

    List<Long> getTransactionByType(String type);
}
