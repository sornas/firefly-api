package net.sornas.firefly.api.http;

import com.google.gson.Gson;
import net.sornas.firefly.api.model.account.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AccountResponse {
    private List<AccountObject> data;

    private class AccountObject {
        String type;
        int id;
        AccountAttributes attributes;
    }

    private class AccountAttributes {
        //    String created_at;
        //    String updated_at;
        boolean active;
        String name;
        String type;
        String account_role;
        //    int currency_id;
        //    String currency_code;
        String currency_symbol;
        int currency_decimal_places;
        double current_balance;
        //    String current_balance_date;
        //    String notes;
        //    String montly_payment_date;
        //    String credit_card_type;
        int account_number;
        //    int iban;
        //    int bic;
        //    int virtual_balance;
        //    int opening_balance;
        //    String opening_balance_date;
        //    String liability_type;
        //    int liability_amount;
        //    String liability_start_date;
        //    double interest;
        //    String interest_period;
        boolean include_net_worth;
    }

    private List<Account> accounts;
    private List<AssetAccount> assetAccounts;
    private List<FlowAccount> flowAccounts;
    private List<RevenueAccount> revenueAccounts;
    private List<ExpenseAccount> expenseAccounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<AssetAccount> getAssetAccounts() {
        return assetAccounts;
    }

    public List<FlowAccount> getFlowAccounts() {
        return flowAccounts;
    }

    public List<RevenueAccount> getRevenueAccounts() {
        return revenueAccounts;
    }

    public List<ExpenseAccount> getExpenseAccounts() {
        return expenseAccounts;
    }

    public static AccountResponse readJson(String json) {
        AccountResponse responseObject = new Gson().fromJson(json, AccountResponse.class);
        List<Account> accounts = new LinkedList<>();
        for (AccountObject accountObject : responseObject.data) {
            AccountAttributes attributes = accountObject.attributes;
            Account account = null;
            switch (attributes.type) {
                case "asset":
                    account = new AssetAccount(attributes.name, accountObject.id);
                    ((AssetAccount) account).setActive(true);
                    ((AssetAccount) account).setIncludeInNetWorth(true);
                    switch (attributes.account_role) {
                        case "defaultAsset":
                            ((AssetAccount) account).setType(AssetAccountType.asset);
                            break;
                        case "sharedAsset":
                            ((AssetAccount) account).setType(AssetAccountType.shared);
                            break;
                        case "savingAsset":
                            ((AssetAccount) account).setType(AssetAccountType.savings);
                            break;
                    }
                    break;
                case "expense":
                    account = new ExpenseAccount(attributes.name, accountObject.id);
                    break;
                case "revenue":
                    account = new RevenueAccount(attributes.name, accountObject.id);
                    break;
            }
            if (account == null) {
                throw new IllegalStateException("Couldn't find type " + attributes.type);
            }
            account.setName(attributes.name);
            account.setId(accountObject.id);
            account.setAccountNumber(attributes.account_number);
            account.setBalance(attributes.current_balance);
            accounts.add(account);
        }
        AccountResponse response = new AccountResponse();
        response.accounts = accounts;

        List<AssetAccount> assetAccounts = new ArrayList<>();
        for (Account account : accounts)
            if (account instanceof AssetAccount)
                assetAccounts.add((AssetAccount) account);
        response.assetAccounts = assetAccounts;

        List<FlowAccount> flowAccounts = new ArrayList<>();
        for (Account account : accounts)
            if (account instanceof FlowAccount)
                flowAccounts.add((FlowAccount) account);
        response.flowAccounts = flowAccounts;

        List<ExpenseAccount> expenseAccounts = new ArrayList<>();
        for (Account account : accounts)
            if (account instanceof ExpenseAccount)
                expenseAccounts.add((ExpenseAccount) account);
        response.expenseAccounts = expenseAccounts;

        List<RevenueAccount> revenueAccounts = new ArrayList<>();
        for (Account account : accounts)
            if (account instanceof RevenueAccount)
                revenueAccounts.add((RevenueAccount) account);
        response.revenueAccounts = revenueAccounts;

        return response;
    }
}

/*
{
   "data": [
      {
         "links": {
            "0": {
               "rel": "self",
               "uri": "/accounts/34"
            },
            "self": "https://firefly.../api/v1/accounts/34"
         }
      },
      {
         "type": "accounts",
         "id": "56",
         "attributes": {
            "created_at": "2019-04-16T17:21:22+02:00",
            "updated_at": "2019-04-16T17:21:22+02:00",
            "active": true,
            "name": "Alfred",
            "type": "revenue",
            "account_role": null,
            "currency_id": 26,
            "currency_code": "SEK",
            "currency_symbol": "kr",
            "currency_decimal_places": 2,
            "current_balance": -14,
            "current_balance_date": "2019-05-25",
            "notes": null,
            "monthly_payment_date": null,
            "credit_card_type": null,
            "account_number": null,
            "iban": null,
            "bic": null,
            "virtual_balance": 0,
            "opening_balance": null,
            "opening_balance_date": null,
            "liability_type": null,
            "liability_amount": null,
            "liability_start_date": null,
            "interest": null,
            "interest_period": null,
            "include_net_worth": true
         },
         "links": {
            "0": {
               "rel": "self",
               "uri": "/accounts/56"
            },
            "self": "https://firefly.../api/v1/accounts/56"
         }
      }
      */