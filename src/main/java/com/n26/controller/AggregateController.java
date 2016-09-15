package com.n26.controller;

import com.n26.datastore.TransactionStore;
import com.n26.exception.NoTransactionFound;
import com.n26.model.AggregateSum;
import com.n26.model.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by pratyushverma on 9/15/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AggregateController {
    @Path("types/{type}")
    @GET
    public List<Long> getTransactionByType(@PathParam("type") String type) {
        return TransactionStore.getInstance().getTransactionByType(type);
    }

    @Path("sum/{transactionId}")
    @GET
    public AggregateSum getSumOfAllTransaction(@PathParam("transactionId") Long transactionId) {
        List<Transaction> transactions;
        try {
            transactions = TransactionStore.getInstance().getAllTransaction(transactionId);
        } catch (NoTransactionFound noTransactionFound) {
            return new AggregateSum(0.0);
        }
        Double total = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        return new AggregateSum(total);
    }

}
