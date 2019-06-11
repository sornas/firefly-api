package net.sornas.firefly.api.model.budget;

public class Budget {
    String name;
    int id;

    public Budget(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Budget parse(String s, int id) {
        //TODO check if budget exists
        return new Budget(s, id);
    }
}
