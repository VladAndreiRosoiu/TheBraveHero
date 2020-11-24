package ro.var.thebravehero.models;

import ro.var.thebravehero.models.abilities.AbilityType;
import ro.var.thebravehero.models.abilities.SpecialAbility;
import ro.var.thebravehero.models.characters.Beast;
import ro.var.thebravehero.models.characters.Hero;

import java.util.List;
import java.util.Optional;
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


    public MagicForest(List<Hero> heroes, List<Beast> beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
    }

    public void initiateGame() {

        currentHero = chooseHero();
        currentBeast = chooseBeast();
        boolean turn = isHeroFirst();
        int damage;
        System.out.println("\t# FIGHT STARTED! #");
        System.out.println("--------------------------");
        System.out.print("HERO LIFE " + currentHero.getLife());
        System.out.print("\tHERO STRENGTH " + currentHero.getStrength());
        System.out.print("\tHERO DEFENCE " + currentHero.getDefence());
        System.out.println();
        System.out.print("BEAST LIFE " + currentBeast.getLife());
        System.out.print("\tBEAST STRENGTH " + currentBeast.getStrength());
        System.out.print("\tBEAST DEFENCE " + currentBeast.getDefence());
        System.out.println();

        do {
            setHeroAbilities();
            if (turn) {
                System.out.println("Hero attacking");
                int initialStrength = currentHero.getStrength();
                Optional<SpecialAbility> specialAbilityOpt = currentHero
                        .getSpecialAbilities()
                        .stream()
                        .filter(specialAbility -> specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE))
                        .findFirst();
                if (specialAbilityOpt.isPresent()) {
                    if (specialAbilityOpt.get().isActivated()) {
                        currentHero.setStrength(currentHero.getStrength() + currentHero.getStrength());
                    }
                }
                damage = heroAttackInitiated();
                System.out.println("Hero damage done " + damage);
                currentBeast.setLife(currentBeast.getLife() - damage);
                currentHero.setStrength(initialStrength);
            } else {
                System.out.println("Beast attacking");
                damage = beastAttackInitiated();
                Optional<SpecialAbility> specialAbilityOpt = currentHero
                        .getSpecialAbilities()
                        .stream()
                        .filter(specialAbility -> specialAbility.getAbilityType().equals(AbilityType.DEFENCE_INCREASE))
                        .findFirst();
                if (specialAbilityOpt.isPresent()) {
                    if (specialAbilityOpt.get().isActivated()) {
                        damage = damage / 2;
                    }
                }
                System.out.println("Beast damage done " + damage);
                currentHero.setLife(currentHero.getLife() - damage);
            }
            System.out.println("ROUND " + round + " ENDED!");
            System.out.println("HERO LIFE " + currentHero.getLife());
            System.out.println("BEAST LIFE " + currentBeast.getLife());
            turn = !turn;
            round++;
            resetHeroAbilities();

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
            if ((1 + random.nextInt(101)) <= specialAbility.getActivationChance()) {
                specialAbility.setActivated(true);
                System.out.println(specialAbility.getName() + " IS ACTIVATED!");
            }
        }
    }

    private void resetHeroAbilities() {
        /*
         *TODO
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            specialAbility.setActivated(false);
        }
    }

    private int heroAttackInitiated() {
        int damage = (currentHero.getStrength() - currentBeast.getDefence());
        if (damage > 0 && damage <= 100) {
            return damage;
        } else if (damage > 100) {
            return 100;
        } else {
            return 0;
        }
    }

    private int beastAttackInitiated() {
        /*
        *TODO
         */
        int damage = (currentBeast.getStrength() - currentHero.getDefence());
        if (damage > 0 && damage <= 100) {
            return damage;
        } else if (damage > 100) {
            return 100;
        } else {
            return 0;
        }
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
