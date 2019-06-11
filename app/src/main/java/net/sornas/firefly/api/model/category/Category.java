package net.sornas.firefly.api.model.category;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public static Category parse(String s) {
        if (s == null) return null;
        if (s.equals("")) return null;
        return new Category(s);
    }
}
