package net.sornas.firefly.api.model.account;

public class AssetAccount extends Account {
    private AssetAccountType type;
    private boolean active;
    private boolean includeInNetWorth;

    public AssetAccount(String name, int id) {
        super(name, id);
    }

    public AssetAccountType getType() {
        return type;
    }

    public void setType(AssetAccountType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean includeInNetWorth() {
        return includeInNetWorth;
    }

    public void setIncludeInNetWorth(boolean includeInNetWorth) {
        this.includeInNetWorth = includeInNetWorth;
    }
}
