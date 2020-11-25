package ro.var.thebravehero.models.characters;

import ro.var.thebravehero.models.abilities.SpecialAbility;

import java.util.List;

/*
 *Child class of Character
 *A hero has in addition from the attributes inherited from Character a list of SpecialAbilities
 */

public class Hero extends Character {

    private List<SpecialAbility> specialAbilities;

    public Hero(String name, int life, int strength, int defence, int speed, int luck, List<SpecialAbility> specialAbilities) {
        super(name, life, strength, defence, speed, luck);
        this.specialAbilities = specialAbilities;
    }

    public List<SpecialAbility> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(List<SpecialAbility> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }
}
