package com.n26.datastore;

import com.n26.exception.NoParentTransactionFound;
import com.n26.exception.NoTransactionFound;
import com.n26.model.Transaction;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Created by pratyushverma on 9/15/16.
 */
public class TransactionStoreTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Transaction t = new Transaction(1.0, "cars", null);
        t.setTransactionId(1l);
        TransactionStore.getInstance().putTransaction(t);
        t = new Transaction(2.0, "cars", 1l);
        t.setTransactionId(2l);
        TransactionStore.getInstance().putTransaction(t);
        t = new Transaction(10.0, "house", null);
        t.setTransactionId(3l);
        TransactionStore.getInstance().putTransaction(t);
    }

    @Test
    public void testGetTransaction() {
        try {
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(1l).getAmount(), 1.0, 0.0);
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(2l).getAmount(), 2.0, 0.0);
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(3l).getAmount(), 10.0, 0.0);

        } catch (NoTransactionFound noTransactionFound) {
            Assert.fail("Exception thrown");
        }
    }

    @Test
    public void testGetTransactionException() throws NoTransactionFound {
        thrown.expect(NoTransactionFound.class);
        TransactionStore.getInstance().getTransaction(-3l);
    }

    @Test
    public void testGetAllTransactionException() throws NoTransactionFound {
        thrown.expect(NoTransactionFound.class);
        TransactionStore.getInstance().getAllTransaction(-3l);
    }

    @Test
    public void testGetAllTransaction() {
        try {
            Assert.assertEquals(TransactionStore.getInstance().getAllTransaction(1l).size(), 2);
            Assert.assertEquals(TransactionStore.getInstance().getAllTransaction(2l).size(), 1);
            Assert.assertEquals(TransactionStore.getInstance().getAllTransaction(3l).size(), 1);
        } catch (NoTransactionFound noTransactionFound) {
            Assert.fail("Exception thrown");
        }
    }

    @Test
    public void testPutTransaction() {
        Transaction t = new Transaction(3.0, "cars", 1l);
        t.setTransactionId(4l);
        try {
            TransactionStore.getInstance().putTransaction(t);
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(4l).getTransactionId(), 4l, 0);
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(4l).getChildTransactions().size(), 0);
            Assert.assertEquals(TransactionStore.getInstance().getTransaction(1l).getChildTransactions().size(), 2);
        } catch (NoParentTransactionFound | NoTransactionFound noParentTransactionFound) {
            Assert.fail("Exception thrown");
        }
    }

    @Test
    public void testGetTransactionByType() {
        Transaction t = new Transaction(100.0, "random1", 1l);
        t.setTransactionId(100l);
        try {
            TransactionStore.getInstance().putTransaction(t);
            Assert.assertEquals(TransactionStore.getInstance().getTransactionByType("random1").size(), 1);
            Assert.assertEquals(TransactionStore.getInstance().getTransactionByType("random2").size(), 0);
        } catch (NoParentTransactionFound noParentTransactionFound) {
            Assert.fail("Exception thrown");
        }
    }
}
