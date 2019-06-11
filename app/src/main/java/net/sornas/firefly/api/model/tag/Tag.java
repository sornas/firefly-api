package net.sornas.firefly.api.model.tag;

import net.sornas.firefly.api.model.transaction.Transaction;

import java.util.LinkedList;
import java.util.List;

public class Tag {
    private String name;
    private List<Transaction> transactions;

    public Tag(String name) {
        this.name = name;
    }

    public static List<Tag> parse(String s) {
        if (s == null)
            return null;
        if (s.equals(""))
            return null;
        List<Tag> tags = new LinkedList<>();
        s = s.replace(" ", "");
        for (String tag: s.split(","))
            tags.add(new Tag(tag));
        return tags;
    }
}
