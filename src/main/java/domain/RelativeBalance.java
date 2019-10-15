package domain;

/**
 * Class containing the relative balance information to be printed.
 */
public class RelativeBalance {
    /**
     * The relative balance amount
     */
    private double amount;
    /**
     * The no:of transactions included
     */
    private int transactionsCount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getValidTransactions() {
        return transactionsCount;
    }

    public void setValidTransactions(int validTransactions) {
        this.transactionsCount = validTransactions;
    }

}
