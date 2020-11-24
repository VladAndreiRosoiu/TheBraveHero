package ro.var.thebravehero.models.abilities;

/*
 *TODO
 */


public class SpecialAbility {

    private String name;
    private int activationChance;
    private String description;
    private AbilityType abilityType;
    private boolean isActive;

    public SpecialAbility(String name, int activationChance, String description, AbilityType abilityType, boolean isActive) {
        this.name = name;
        this.activationChance = activationChance;
        this.description = description;
        this.abilityType = abilityType;
        this.isActive = isActive;
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

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
