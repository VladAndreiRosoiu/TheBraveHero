package ro.var.thebravehero.model;

import ro.var.thebravehero.model.chars.Beast;
import ro.var.thebravehero.model.chars.Hero;

import java.util.List;

public class MagicForest {

    private final List<Hero> heroes;
    private final List<Beast> beasts;

    public MagicForest(List<Hero> heroes, List<Beast> beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
    }

    public void initiateGame() {
        heroes.forEach(hero -> System.out.println("Name : "+hero.getName() +
                " Life : "+ hero.getLife() +
                " Strength : "+hero.getStrength() +
                " Defence : "+hero.getDefence() +
                " Speed : "+hero.getSpeed() +
                " Luck : "+hero.getLuck()));
        heroes.forEach(hero -> hero.getSpecialAbilities().forEach(specialAbility -> System.out.println(specialAbility.getName())));
        beasts.forEach(beast -> System.out.println("Name : "+ beast.getName() +
                " Life : "+beast.getLife() +
                " Strength : "+beast.getStrength() +
                " Defence : "+beast.getDefence() +
                " Speed : "+beast.getSpeed() +
                " Luck : "+beast.getLuck()));
    }

}
