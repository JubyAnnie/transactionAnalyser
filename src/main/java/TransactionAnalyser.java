import domain.RelativeBalance;
import dto.Transaction;
import service.TransactionManager;
import service.impl.TransactionManagerImpl;
import java.util.List;
import java.util.Scanner;

/**
 * Class containing the main method to run the transaction analyser program.
 */
public class TransactionAnalyser {

    public static void main(String args[]) {
        TransactionManager transactionManager = new TransactionManagerImpl();
        List<Transaction> transactions = transactionManager.readFromCSV();
        //get the input parameters
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Please enter the account Id");
        String accountId = scan.next();
        System.out.println("Please enter the from date");
        String fromDate = scan.next();
        System.out.println("Please enter the to date");
        String toDate = scan.next();
        //call the method to calculate the relative balance and no:of included transactions.
        RelativeBalance relativeBalance = transactionManager.calculateRelativeBalanceDetails(transactions, accountId, fromDate
                , toDate);
        //print the relative balance and no:of included transactions.
        if (relativeBalance != null) {
            System.out.println("Relative balance for the period is:" + relativeBalance.getAmount());
            System.out.println("Number of transactions included is:" + relativeBalance.getValidTransactions());
        }
    }
}
