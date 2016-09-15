package com.n26.datastore;

import com.n26.exception.NoParentTransactionFound;
import com.n26.exception.NoTransactionFound;
import com.n26.model.Transaction;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pratyushverma on 9/15/16.
 */
public class TransactionStore implements ITransactionStore {
    private static ITransactionStore ourInstance = new TransactionStore();

    public static ITransactionStore getInstance() {
        return ourInstance;
    }

    private TransactionStore() {
    }

    private HashMap<Long, Transaction> primaryStore = new HashMap<>();
    private HashMap<String, List<Long>> typeView = new HashMap<>();

    @Override
    public Transaction getTransaction(@NotNull Long transactionId) throws NoTransactionFound {
        if (primaryStore.containsKey(transactionId)) {
            return primaryStore.get(transactionId);
        } else {
            throw new NoTransactionFound();
        }
    }

    @Override
    public List<Transaction> getAllTransaction(@NotNull Long transactionId) throws NoTransactionFound {
        List<Transaction> transactions = new ArrayList<>();
        Transaction parentTransaction = getTransaction(transactionId);
        if (parentTransaction == null) {
            throw new NoTransactionFound();
        }
        transactions.add(parentTransaction);
        for (Long childTransactionId : parentTransaction.getChildTransactions()) {
            transactions.addAll(getAllTransaction(childTransactionId));
        }
        return transactions;
    }

    @Override
    public boolean putTransaction(@NotNull Transaction transaction) throws NoParentTransactionFound {
        if (transaction.getParentId() != null) {
            // Since this is a reference of in-memory object, this does not need to be explicitly persisted
            Transaction parentTransaction = primaryStore.get(transaction.getParentId());
            if (parentTransaction == null) {
                throw new NoParentTransactionFound();
            } else if (parentTransaction.getChildTransactions() == null) {
                parentTransaction.setChildTransactions(new ArrayList<>());
            }
            parentTransaction.getChildTransactions().add(transaction.getTransactionId());
        }
        primaryStore.put(transaction.getTransactionId(), transaction);
        if (typeView.containsKey(transaction.getType())) {
            typeView.get(transaction.getType()).add(transaction.getTransactionId());
        } else {
            ArrayList<Long> transactionList = new ArrayList<>();
            transactionList.add(transaction.getTransactionId());
            typeView.put(transaction.getType(), transactionList);
        }

        return true;
    }

    @Override
    public List<Long> getTransactionByType(@NotNull String type) {
        return typeView.getOrDefault(type, new ArrayList<>());
    }


}
