package ro.var.thebravehero.model.chars;

import java.util.List;

public class Hero extends Character {

    private List<SpecialAbility> specialAbilities;

    public Hero(String name, int life, int strength, int defence, int speed, int luck, List<SpecialAbility> specialAbilities) {
        super(name, life, strength, defence, speed, luck);
        this.specialAbilities=specialAbilities;
    }

    public List<SpecialAbility> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(List<SpecialAbility> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }
}
