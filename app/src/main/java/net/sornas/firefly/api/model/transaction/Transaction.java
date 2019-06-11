package net.sornas.firefly.api.model.transaction;

import net.sornas.firefly.api.model.account.Account;
import net.sornas.firefly.api.model.budget.Budget;
import net.sornas.firefly.api.model.category.Category;
import net.sornas.firefly.api.model.tag.Tag;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public abstract class Transaction {
    private String description;
    private double amount;
    private LocalDateTime dateTime;
    private Account source;
    private Account destination;
    private Category category;
    private Budget budget;
    private List<Tag> tags;

    public Transaction(String description, double amount, LocalDateTime dateTime, Account source, Account destination,
                       Category category, Budget budget, List<Tag> tags) {
        this.description = description;
        this.amount = amount;
        this.dateTime = dateTime;
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.budget = budget;
        this.tags = tags;
    }
    public Transaction(String description, double amount, String dateTime, Account source, Account destination,
                       Category category, Budget budget, List<Tag> tags) {
        this.description = description;
        this.amount = amount;
        this.dateTime = ZonedDateTime.parse(dateTime).toLocalDateTime();
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.budget = budget;
        this.tags = tags;
    }
}
