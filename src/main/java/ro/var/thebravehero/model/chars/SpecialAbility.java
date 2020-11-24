package ro.var.thebravehero.model.chars;

public class SpecialAbility {

    private String name;
    private int activationChance;
    private String description;

    public SpecialAbility(String name, int activationChance, String description) {
        this.name = name;
        this.activationChance = activationChance;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivationChance() {
        return activationChance;
    }

    public void setActivationChance(int activationChance) {
        this.activationChance = activationChance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
