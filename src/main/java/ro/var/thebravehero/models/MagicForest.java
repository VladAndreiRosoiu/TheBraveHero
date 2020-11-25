package ro.var.thebravehero.models;

import ro.var.thebravehero.models.abilities.AbilityType;
import ro.var.thebravehero.models.abilities.SpecialAbility;
import ro.var.thebravehero.models.characters.Beast;
import ro.var.thebravehero.models.characters.Hero;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 *TODO
 */

public class MagicForest {

    private Scanner sc = new Scanner(System.in);
    private final List<Hero> heroes;
    private final List<Beast> beasts;
    private Hero currentHero;
    private Beast currentBeast;
    private int round = 1;
    private final Random random = new Random();
    private boolean turn;


    public MagicForest(List<Hero> heroes, List<Beast> beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
    }

    public void startGame() {

        /*
        *TODO
         */

        currentHero = chooseHero();
        currentBeast = chooseBeast();
        turn = isHeroFirst();
        printInitialStatistics();


        System.out.println("Are you ready to start?");
        String answer = sc.next();
        boolean continueGame = answer.equals("yes");
        while (continueGame && round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0) {
            System.out.println("ROUND "+round + " STARTED!");
            setHeroAbilities();
            int damage = playRound();
            printRoundStatistics(damage);
            resetHeroAbilities();
            turn = !turn;
            round++;
            System.out.println("Continue?");
            answer = sc.next();
            continueGame = answer.equals("yes");
        }
        if (currentHero.getLife() <= 0) {
            System.out.println("Hero died!");
        } else if (currentBeast.getLife() <= 0) {
            System.out.println("Beast died!");
        } else if (round > 20) {
            System.out.println("It's a tie! Nobody died!");
        }
        System.out.println("Do you want to start another adventure?");
        answer=sc.next();
        if (answer.equalsIgnoreCase("yes")){
            round = 1 ;
            startGame();
        }else {
            System.out.println("Thank you for playing!");
        }

    }



    private Hero chooseHero() {
        /*
         *In case we add more heroes, this method will allow the player to choose one of them!
         */

        //TODO
        System.out.println("Choose hero");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(i + 1 + " - " + heroes.get(i).getName());
            System.out.print("HERO LIFE " + heroes.get(i).getLife());
            System.out.print("\tHERO STRENGTH " + heroes.get(i).getStrength());
            System.out.print("\tHERO DEFENCE " + heroes.get(i).getDefence());
            System.out.print("\tHERO SPEED " + heroes.get(i).getSpeed());
            System.out.print("\tHERO LUCK " + heroes.get(i).getLuck());
        }
        System.out.println();
        System.out.println("CHOOSE YOUR HERO!");
        int choice = sc.nextInt();
        return heroes.get(choice - 1);
    }

    private Beast chooseBeast() {
        /*
         *In case we add more beasts, this method will randomly return one of the beasts!
         */
        return beasts.get(random.nextInt(beasts.size()));
    }

    private boolean isHeroFirst() {
        /*
         *TODO
         */
        if (currentHero.getSpeed() > currentBeast.getSpeed()) {
            return true;
        } else if (currentHero.getSpeed() == currentBeast.getSpeed()) {
            return currentHero.getLuck() > currentBeast.getLuck();
        } else {
            return false;
        }
    }

    private void setHeroAbilities() {
        /*
         *TODO
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            if ((1 + random.nextInt(100)) <= specialAbility.getActivationChance()) {
                specialAbility.setActive(true);
            }
        }
    }

    private void resetHeroAbilities() {
        /*
         *TODO
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            specialAbility.setActive(false);
        }
    }

    private int initiateAttack(int strength, int defence) {
        /*
         *TODO
         */
        int damage = strength - defence;
        if (damage > 0 && damage <= 100) {
            return damage;
        } else if (damage > 100) {
            return 100;
        } else {
            return 0;
        }
    }

    private boolean defendAttack(int luck) {
        /*
         *TODO
         */
        return (1 + random.nextInt(100) <= luck);
    }

    private int playRound() {
        /*
         *TODO
         */
        int damage;
        if (turn) {
            System.out.println("HERO ATTACKING!");
            int initialStrength = currentHero.getStrength();
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
                if (specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE)
                        && specialAbility.isActive()) {
                    currentHero.setStrength(currentHero.getStrength() * 2);
                }
            }
            if (defendAttack(currentBeast.getLuck())) {
                System.out.println("BEAST DEFENDED ATTACK!");
                damage = 0;
            } else {
                damage = initiateAttack(currentHero.getStrength(), currentBeast.getDefence());
            }
            currentBeast.setLife(currentBeast.getLife() - damage);
            currentHero.setStrength(initialStrength);
        } else {
            System.out.println("BEAST ATTACKING!");
            if (defendAttack(currentHero.getLuck())) {
                damage = 0;
                System.out.println("HERO DEFENDED ATTACK!");
            } else {
                damage = initiateAttack(currentHero.getStrength(), currentBeast.getDefence());
            }
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
                if (specialAbility.getAbilityType().equals(AbilityType.DEFENCE_INCREASE)
                        && specialAbility.isActive()) {
                    damage = damage / 2;
                }
            }
            currentHero.setLife(currentHero.getLife() - damage);
        }
        return damage;
    }

    private void printInitialStatistics() {
        /*
         *TODO
         */
        System.out.println("\t# FIGHT STARTED! #");
        System.out.println("--------------------------");
        System.out.print("HERO LIFE " + currentHero.getLife());
        System.out.print("\tHERO STRENGTH " + currentHero.getStrength());
        System.out.print("\tHERO DEFENCE " + currentHero.getDefence());
        System.out.print("\tHERO SPEED " + currentHero.getSpeed());
        System.out.print("\tHERO LUCK " + currentHero.getLuck());
        System.out.println();
        System.out.print("BEAST LIFE " + currentBeast.getLife());
        System.out.print("\tBEAST STRENGTH " + currentBeast.getStrength());
        System.out.print("\tBEAST DEFENCE " + currentBeast.getDefence());
        System.out.print("\tBEAST SPEED " + currentBeast.getSpeed());
        System.out.print("\tBEAST LUCK " + currentBeast.getLuck());
        System.out.println();
        System.out.println("--------------------------");
    }

    private void printRoundStatistics(int damage) {
        /*
         *TODO
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            if (specialAbility.isActive()) {
                System.out.println(specialAbility.getName() + " was activated this round!");
                System.out.println(specialAbility.getDescription());
            }
        }
        System.out.println("DAMAGE DONE WAS " + damage);
        System.out.println("HERO LIFE " + currentHero.getLife());
        System.out.println("BEAST LIFE " + currentBeast.getLife());
        System.out.println("ROUND " + round + " ENDED!");
    }

}
