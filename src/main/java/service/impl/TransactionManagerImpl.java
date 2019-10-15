package service.impl;

import domain.RelativeBalance;
import dto.Transaction;
import service.TransactionManager;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of Service class responsible for processing financial transactions.
 */
public class TransactionManagerImpl implements TransactionManager {
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Reads the transaction details from the input csv file.
     *
     * @return list of transactions.
     */
    public List<Transaction> readFromCSV() {

        List<Transaction> transactions = new ArrayList<>();
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream is = classLoader.getResourceAsStream("input.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            transactions = br.lines().map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("The input file is not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
        }
        return transactions;
    }

    /**
     * Calculates the relative balance amount and the no:of transactions included
     * @param allTransactions      the list of transactions
     * @param accountId         the account id to be considered
     * @param fromDateString    the from date
     * @param toDateString      the to date
     * @return                  relative balance details.
     */
    public RelativeBalance calculateRelativeBalanceDetails(List<Transaction> allTransactions, String accountId, String fromDateString, String toDateString) {
        List<Transaction> transactions = new ArrayList<>();
        RelativeBalance relativeBalance = new RelativeBalance();
        int count = 0;
        double totalAmount = 0;
        try {
            if (allTransactions.isEmpty() || accountId == null || accountId.isEmpty()
                    || fromDateString == null || fromDateString.isEmpty()
                    || toDateString == null || toDateString.isEmpty()) {
                throw new InvalidParameterException("The input parameters cannot be null");
            } else {
                //parses the to and from date string into date format.
                Date fromDate = df.parse(fromDateString);
                Date toDate = df.parse(toDateString);
                //filters out the transactions for the account id to be considered and
                // also removes the REVERSAL transactions and the corresponding related transaction (the transaction that's reversed).
                allTransactions.stream().filter(transaction -> (transaction.getFromAccountId().equals(accountId.trim()) || transaction.getToAccountId().equals(accountId.trim())))
                        .forEach(transaction -> {
                            if (transaction.getTransactionType().trim().equals("REVERSAL")  && !transactions.isEmpty()) {
                                transactions.removeIf(transaction1 -> transaction1.getTransactionId().trim().equals(transaction.getRelatedTransaction().trim()));
                            } else {
                                transactions.add(transaction);
                            }
                        });
                //iterate through the filtered out transaction and consider only the transactions within the specified time frame.
                for (Transaction t : transactions) {
                    if ((t.getCreateAt().after(fromDate) && t.getCreateAt().before(toDate))) {
                        //if amount is transferred from the account subtract the amount from the total amount.
                        //else add the amount to the total amount.
                        if (t.getFromAccountId().equals(accountId.trim())) {
                            totalAmount -= t.getAmount();
                        } else if (t.getToAccountId().equals(accountId.trim())) {
                            totalAmount += t.getAmount();
                        }
                        //increment the count of included transactions.
                        count++;
                    }
                }
                relativeBalance.setAmount(totalAmount);
                relativeBalance.setValidTransactions(count);
            }
        } catch (ParseException e) {
            System.out.print("DatTime parse exception" + e);
        } catch (InvalidParameterException e) {
            System.out.println("Invalid input parameters" + e.getMessage());
        }
        return relativeBalance;
    }

    /**
     * Maps each column in the csv file to the fields in the Transaction DTO.
     */
    private Function<String, Transaction> mapToItem = (line) -> {
        Transaction transaction = new Transaction();
        try {
            String[] p = line.split(",");// a CSV has comma separated lines
            transaction.setTransactionId(p[0].trim());
            transaction.setFromAccountId(p[1].trim());
            transaction.setToAccountId(p[2].trim());
            transaction.setCreateAt(df.parse(p[3].trim()));
            transaction.setAmount(Float.parseFloat(p[4].trim()));
            transaction.setTransactionType(p[5].trim());
            //related transaction is available only for REVERSAL transactions.
            if (p.length > 6) {
                if (p[6] != null && p[6].trim().length() > 0) {
                    transaction.setRelatedTransaction(p[6].trim());
                }
            }
        } catch (ParseException e) {
            System.out.println("An error occurred while parsing the dates" + e.getMessage());
        }
        return transaction;
    };
}
