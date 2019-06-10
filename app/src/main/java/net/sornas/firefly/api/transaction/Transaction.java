package net.sornas.firefly.api.transaction;

import net.sornas.firefly.api.account.Account;
import net.sornas.firefly.api.account.AssetAccount;
import net.sornas.firefly.api.account.FlowAccount;
import net.sornas.firefly.api.budget.Budget;
import net.sornas.firefly.api.category.Category;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public abstract class Transaction {
    private String description;
    private double amount;
    private LocalDateTime dateTime;
    private Account source;
    private Account destination;
    private Category category;
    private Budget budget;

    public Transaction(String description, double amount, LocalDateTime dateTime, Account source, Account destination,
                       Category category, Budget budget) {
        this.description = description;
        this.amount = amount;
        this.dateTime = dateTime;
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.budget = budget;
    }
    public Transaction(String description, double amount, String dateTime, Account source, Account destination,
                       Category category, Budget budget) {
        this.description = description;
        this.amount = amount;
        this.dateTime = ZonedDateTime.parse(dateTime).toLocalDateTime();
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.budget = budget;
    }
}
