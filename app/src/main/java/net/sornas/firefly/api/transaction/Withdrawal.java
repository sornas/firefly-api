package net.sornas.firefly.api.transaction;

import net.sornas.firefly.api.account.AssetAccount;
import net.sornas.firefly.api.account.FlowAccount;
import net.sornas.firefly.api.budget.Budget;
import net.sornas.firefly.api.category.Category;
import net.sornas.firefly.api.tag.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class Withdrawal extends Transaction{
    public Withdrawal(String description, double amount, LocalDateTime dateTime,
                      AssetAccount source, FlowAccount destination, Category category, Budget budget, List<Tag> tags) {
        super(description, amount, dateTime, source, destination, category, budget, tags);
    }
    public Withdrawal(String description, double amount, String dateTime,
                      AssetAccount source, FlowAccount destination, Category category, Budget budget, List<Tag> tags) {
        super(description, amount, dateTime, source, destination, category, budget, tags);
    }
}
