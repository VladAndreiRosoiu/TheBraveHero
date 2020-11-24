package ro.var.thebravehero.models;

import ro.var.thebravehero.models.abilities.*;
import ro.var.thebravehero.models.characters.*;
import java.util.*;

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

        currentHero = chooseHero();
        currentBeast = chooseBeast();
        turn = isHeroFirst();
        printInitialStatistics();

        do {
            setHeroAbilities();
            int damage = gameLogic();
            printRoundStatistics(damage);
            System.out.println("Continue? Y/N");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("y")) {
                System.out.println("STARTING NEW ROUND!!!");
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("CHICKEN!!!!");
                break;
            }
            if (currentHero.getLife() <= 0) {
                System.out.println("Hero died!");
            } else if (currentBeast.getLife() <= 0) {
                System.out.println("Beast died!");
            } else if (round == 0) {
                System.out.println("It's a tie! Nobody died!");
            }
            resetHeroAbilities();
            turn = !turn;
            round++;
        } while (round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0);

    }

    private Hero chooseHero() {
        /*
         *In case we add more heroes, this method will allow the player to choose one of them!
         */

        //TODO

//        System.out.println("Choose hero");
//        for (int i = 0; i < heroes.size(); i++) {
//            System.out.println(i + 1 + " - " + heroes.get(i).getName());
//        }
//        int choice = sc.nextInt();
//        return heroes.get(choice - 1);
        return heroes.get(random.nextInt(heroes.size()));
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

    private int heroAttackInitiated() {
        /*
         *TODO
         */
        int damage = (currentHero.getStrength() - currentBeast.getDefence());
        if ((1 + random.nextInt(100) <= currentHero.getLuck())) {
            System.out.println("ATTACK DEFENDED!");
            return 0;
        } else {
            if (damage > 0 && damage <= 100) {
                return damage;
            } else if (damage > 100) {
                return 100;
            } else {
                return 0;
            }
        }
    }

    private int beastAttackInitiated() {
        /*
         *TODO
         */
        int damage = (currentBeast.getStrength() - currentHero.getDefence());
        if ((1 + random.nextInt(100) <= currentBeast.getLuck())) {
            System.out.println("ATTACK DEFENDED!");
            return 0;
        } else {
            if (damage > 0 && damage <= 100) {
                return damage;
            } else if (damage > 100) {
                return 100;
            } else {
                return 0;
            }
        }
    }

    private int gameLogic() {
        /*
         *TODO
         */
        int damage;
        if (turn) {
            int initialStrength = currentHero.getStrength();
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
                if (specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE)
                        && specialAbility.isActive()) {
                    currentHero.setStrength(currentHero.getStrength() * 2);
                }
            }
            damage = heroAttackInitiated();
            currentBeast.setLife(currentBeast.getLife() - damage);
            currentHero.setStrength(initialStrength);
        } else {
            damage = beastAttackInitiated();

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
    }

    private void printRoundStatistics(int damage) {
        /*
         *TODO
         */
        System.out.println("ROUND " + round + " ENDED!");
        if (turn){
            System.out.println("HERO ATTACKED FOR " + damage);
        }else {
            System.out.println("BEAST ATTACKED FOR " + damage);
        }
        for (SpecialAbility specialAbility:currentHero.getSpecialAbilities()){
            if (specialAbility.isActive()){
                System.out.println(specialAbility.getName() + " was activated this round!");
            }
        }
        System.out.println("HERO LIFE " + currentHero.getLife());
        System.out.println("BEAST LIFE " + currentBeast.getLife());
    }


//    private boolean continueGame() {
//        try {
//            System.out.println("Continue? Y/N");
//            String answer = sc.next();
//            if (answer.equalsIgnoreCase("y")) {
//                System.out.println("STARTING NEW ROUND!!!");
//                return true;
//            } else if (answer.equalsIgnoreCase("n")) {
//                System.out.println("CHICKEN!!!!");
//                return false;
//            }
//        } catch (InputMismatchException e) {
//            System.out.println("Invalid input!");
//            sc = new Scanner(System.in);
//            continueGame();
//        }
//    }
}
