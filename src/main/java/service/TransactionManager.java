package service;

import domain.RelativeBalance;
import dto.Transaction;

import java.util.List;

/**
 * Service class responsible for processing financial transactions.
 */
public interface TransactionManager {
    /**
     * Reads the transaction details from the input csv file.
     *
     * @return list of transactions.
     */
    List<Transaction> readFromCSV();

    /**
     * Calculates the relative balance amount and the no:of transactions included
     *
     * @param allTransactions the list of transactions
     * @param accountId       the account id to be considered
     * @param fromDateString  the from date
     * @param toDateString    the to date
     * @return relative balance details.
     */
    RelativeBalance calculateRelativeBalanceDetails(List<Transaction> allTransactions, String accountId, String fromDateString, String toDateString);
}
