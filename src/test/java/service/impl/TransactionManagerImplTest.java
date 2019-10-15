package service.impl;

import domain.RelativeBalance;
import dto.Transaction;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for TransactionManagerImpl.
 */

public class TransactionManagerImplTest {
    /**
     * Class under test.
     */
    private TransactionManagerImpl manager;
    /**
     * allows centralised control of all mock objects.
     */
    private IMocksControl ctrl;

    /**
     * Initialises collaborators.
     *
     * @throws Exception on exception
     */
    @Before
    public final void setUp() throws Exception {
        ctrl = EasyMock.createControl();
        manager = new TransactionManagerImpl();

    }

    /**
     * Destroys collaborators.
     *
     * @throws Exception on exception
     */
    @After
    public final void tearDown() throws Exception {
        manager = null;
        ctrl = null;
    }

    /**
     * Test method for calculateRelativeBalance().
     */
    @Test
    public final void calculateRelativeBalance_Expects_RelativeBalanceAndCount() {
        ctrl.replay();
        List<Transaction> transactions = buildTransactions();
        RelativeBalance rb = manager.calculateRelativeBalanceDetails(transactions, "ACC334455", "20/10/2018 12:00:00", "20/10/2018 17:00:00");
        ctrl.verify();
        assertNotNull(rb.getAmount());
        assertEquals("The relative balance amount must be -25", -25.00, rb.getAmount(), 0);
        assertEquals("The no:of valid transactions must be 1", 1, rb.getValidTransactions());
    }


    /**
     * Test method for readFromCSV().
     */
    @Test
    public final void readFromCSV_Expects_TransactionList() {
        ctrl.replay();
        List<Transaction> transactions = manager.readFromCSV();
        ctrl.verify();
        assertNotNull(transactions);
        assertEquals("The transaction id of the first transaction", "TX10001", transactions.get(0).getTransactionId());
        assertEquals("The related transaction in TX10004 is TX10002", "TX10002", transactions.get(3).getRelatedTransaction());
    }


    /**
     * Helper method to create a list of transactions.
     *
     * @return list of transactions.
     */

    private List<Transaction> buildTransactions() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<Transaction> transactions = new ArrayList<>();
        try {
            Transaction t1 = new Transaction();
            t1.setTransactionId("TX10001");
            t1.setFromAccountId("ACC334455");
            t1.setToAccountId("ACC778899");
            t1.setAmount(25.00);
            t1.setCreateAt(df.parse("20/10/2018 12:47:55"));
            t1.setTransactionType("PAYMENT");
            transactions.add(t1);

            Transaction t2 = new Transaction();
            t2.setTransactionId("TX10002");
            t2.setFromAccountId("ACC334455");
            t2.setToAccountId("ACC998877");
            t2.setAmount(10.50);
            t2.setCreateAt(df.parse("20/10/2018 17:33:43"));
            t2.setTransactionType("PAYMENT");
            transactions.add(t2);

            Transaction t3 = new Transaction();
            t3.setTransactionId("TX10003");
            t3.setFromAccountId("ACC998877");
            t3.setToAccountId("ACC778899");
            t3.setAmount(5.00);
            t3.setCreateAt(df.parse("20/10/2018 18:00:00"));
            t3.setTransactionType("PAYMENT");
            transactions.add(t3);

            Transaction t4 = new Transaction();
            t4.setTransactionId("TX10004");
            t4.setFromAccountId("ACC334455");
            t4.setToAccountId("ACC998877");
            t4.setAmount(10.50);
            t4.setCreateAt(df.parse("20/10/2018 19:45:00"));
            t4.setRelatedTransaction("TX10002");
            t4.setTransactionType("REVERSAL");
            transactions.add(t4);

            Transaction t5 = new Transaction();
            t5.setTransactionId("TX10005");
            t5.setFromAccountId("ACC334455");
            t5.setToAccountId("ACC778899");
            t5.setAmount(7.25);
            t5.setCreateAt(df.parse("21/10/2018 09:30:00"));
            t5.setTransactionType("PAYMENT");
            transactions.add(t5);

        } catch (ParseException e) {
            System.out.println("An error occurred while parsing the dates" + e.getMessage());
        }
        return transactions;
    }

}
