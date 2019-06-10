package net.sornas.firefly.api.http;

import java.util.List;

public class SingleAccountTransactionListResponse {
    private List<SingleAccountTransactionResponseData> data;

    private class SingleAccountTransactionResponseData {
        String type; // "transactions" förhoppningsvis
        int id;
        private SingleAccountTransactionResponseDataAttribute attributes;

        private class SingleAccountTransactionResponseDataAttribute {
            private SingleAccountTransaction transactions;  // plural men inte en lista ??

            private class SingleAccountTransaction {
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
                String destionation_name;
                String destionation_type; // se source_type
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
    }
}
