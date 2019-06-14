package net.sornas.firefly.api.http;

import com.google.gson.Gson;
import net.sornas.firefly.api.model.account.AssetAccount;
import net.sornas.firefly.api.model.account.ExpenseAccount;
import net.sornas.firefly.api.model.account.RevenueAccount;
import net.sornas.firefly.api.model.budget.Budget;
import net.sornas.firefly.api.model.category.Category;
import net.sornas.firefly.api.model.tag.Tag;
import net.sornas.firefly.api.model.transaction.Deposit;
import net.sornas.firefly.api.model.transaction.Transaction;
import net.sornas.firefly.api.model.transaction.Transfer;
import net.sornas.firefly.api.model.transaction.Withdrawal;

import java.util.LinkedList;
import java.util.List;

public class SingleAccountTransactionListResponse {
    private List<SingleAccountTransactionResponseData> data;

    private class SingleAccountTransactionResponseData {
        String type; // "transactions" förhoppningsvis
        int id;
        private SingleAccountTransactionResponseDataAttributes attributes;

        private class SingleAccountTransactionResponseDataAttributes {
            String type; // [ withdrawal, expense, deposit, transfer, opening balance, reconciliation ]
            String date; // example: 2018-09-17T12:46:47+01:00
            double amount;
            String description;
            int source_id;
            String source_name;
            String source_type; // [ Default account, Cash account, Asset account, Expense account, Revenue account,
                                // Initial balance account, Beneficiary account, Import account,
                                // Reconciliation account, Loan, Debt, Mortgage ]
            int destination_id;
            String destination_name;
            String destination_type; // se source_type
            int budget_id;
            String budget_name;
            String category_name;
            int bill_id;
            String bill_name;
            boolean reconciled;
            String notes;
            String tags;  // "Array of tags. Not sure how to format it." troligen {tag1, tag2, tag3}
        }
    }

    private List<Transaction> transactions;
    private List<Deposit> deposits;
    private List<Withdrawal> withdrawals;
    private List<Transfer> transfers;

    public SingleAccountTransactionListResponse(String json) {
        SingleAccountTransactionListResponse responseObject = new Gson().fromJson(json,
                SingleAccountTransactionListResponse.class);
        transactions = new LinkedList<>();
        deposits = new LinkedList<>();
        withdrawals = new LinkedList<>();
        transfers = new LinkedList<>();
        for (SingleAccountTransactionResponseData transactionData: responseObject.data) {
            SingleAccountTransactionResponseData.SingleAccountTransactionResponseDataAttributes
                    attributes = transactionData.attributes;
            Transaction transaction;
            switch (attributes.type) {
                case "Withdrawal":
                    transaction = new Withdrawal(
                            attributes.description,
                            attributes.amount,
                            attributes.date,
                            new AssetAccount(attributes.source_name, attributes.source_id),
                            new ExpenseAccount(attributes.destination_name, attributes.destination_id),
                            Category.parse(attributes.category_name),
                            Budget.parse(attributes.budget_name, attributes.budget_id),
                            Tag.parse(attributes.tags)
                    );
                    withdrawals.add((Withdrawal) transaction);
                    break;
                case "Deposit":
                    transaction = new Deposit(
                            attributes.description,
                            attributes.amount,
                            attributes.date,
                            new RevenueAccount(attributes.source_name, attributes.source_id),
                            new AssetAccount(attributes.destination_name, attributes.destination_id),
                            Category.parse(attributes.category_name),
                            Budget.parse(attributes.budget_name, attributes.budget_id),
                            Tag.parse(attributes.tags)
                    );
                    deposits.add((Deposit) transaction);
                    break;
                case "Transfer":
                    transaction = new Transfer(
                            attributes.description,
                            attributes.amount,
                            attributes.date,
                            new AssetAccount(attributes.source_name, attributes.source_id),
                            new AssetAccount(attributes.destination_name, attributes.destination_id),
                            Category.parse(attributes.category_name),
                            Budget.parse(attributes.budget_name, attributes.budget_id),
                            Tag.parse(attributes.tags)
                    );
                    transfers.add((Transfer) transaction);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + attributes.type);
            }
            transactions.add(transaction);
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void merge(AccountResponse accountResponse) {}  //TODO använd samma account-objekt
}
