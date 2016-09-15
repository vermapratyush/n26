package com.n26.controller;

import com.n26.datastore.TransactionStore;
import com.n26.exception.NoParentTransactionFound;
import com.n26.exception.NoTransactionFound;
import com.n26.model.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by pratyushverma on 9/15/16.
 */
@Path("/transaction/{transactionId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionController {

    @PUT
    public Response putTransaction(@PathParam("transactionId") Long transactionId, Transaction transaction) {
        transaction.setTransactionId(transactionId);
        boolean success;
        try {
            success = TransactionStore.getInstance().putTransaction(transaction);
        } catch (NoParentTransactionFound noParentTransactionFound) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (success) {
            return Response.accepted().build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    public Response getTransaction(@PathParam("transactionId") Long transactionId) {
        Transaction transaction;
        try {
            transaction = TransactionStore.getInstance().getTransaction(transactionId);
        } catch (NoTransactionFound noTransactionFound) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(transaction).build();
    }
}
