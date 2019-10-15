package dto;

import java.util.Date;

/**
 * DTO class for the transaction object.
 */
public class Transaction {
    /**
     * The transaction id
     */
    private String transactionId;
    /**
     * The account id from which the transaction is made
     */
    private String fromAccountId;
    /**
     * The account id to which the transaction is made
     */
    private String toAccountId;
    /**
     * The time at which the transaction is made
     */
    private Date createAt;
    /**
     * The amount transferred
     */
    private double amount;
    /**
     * The type of transaction (PAYMENT or REVERSAL)
     */
    private String transactionType;
    /**
     * In case of REVERSAL transactions, this will have the id of the transaction being reversed
     */
    private String relatedTransaction;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        this.relatedTransaction = relatedTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
