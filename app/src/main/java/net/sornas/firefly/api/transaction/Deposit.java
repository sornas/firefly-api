package net.sornas.firefly.api.transaction;

import net.sornas.firefly.api.account.AssetAccount;
import net.sornas.firefly.api.account.FlowAccount;
import net.sornas.firefly.api.budget.Budget;
import net.sornas.firefly.api.category.Category;

import java.time.LocalDateTime;

public class Deposit extends Transaction {
    public Deposit(String description, double amount, LocalDateTime dateTime,
                   FlowAccount source, AssetAccount destination, Category category, Budget budget) {
        super(description, amount, dateTime, source, destination, category, budget);
    }
    public Deposit(String description, double amount, String dateTime,
                   FlowAccount source, AssetAccount destination, Category category, Budget budget) {
        super(description, amount, dateTime, source, destination, category, budget);
    }
}
