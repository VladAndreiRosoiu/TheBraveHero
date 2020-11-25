package ro.var.thebravehero.models;

import ro.var.thebravehero.models.abilities.AbilityType;
import ro.var.thebravehero.models.abilities.SpecialAbility;
import ro.var.thebravehero.models.characters.Beast;
import ro.var.thebravehero.models.characters.Hero;

import java.util.InputMismatchException;
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
    private boolean gameOn;

    public MagicForest(List<Hero> heroes, List<Beast> beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
    }

    public void startGame() {

        /*
         *TODO
         */
        printIntroduction();
        setHero();
        setBeast();
        printFightIntroduction();
        turn = isHeroFirst();
        printInitialStatistics();
        System.out.println("Are you ready to start?");
        retreatFromBattle();
        printFirstAttacker();
        while (gameOn && round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0) {
            System.out.println("Round " + round + " started!");
            setHeroAbilities();
            int damage = playRound();
            printRoundStatistics(damage);
            resetHeroAbilities();
            turn = !turn;
            round++;
            if (round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0) {
                System.out.println("Continue or retreat?");
                retreatFromBattle();
            } else {
                break;
            }
        }
        if (currentHero.getLife() <= 0) {
            printDefeat();
        } else if (currentBeast.getLife() <= 0) {
            printVictory();
        } else if (round > 20) {
            printTie();
        }
        round = 1;
    }

    private void setHero() {
        /*
         *In case we add more heroes, this method will allow the player to choose one of them!
         */

        //TODO

        try {
            for (int i = 0; i < heroes.size(); i++) {
                System.out.println(i + 1 + " - Hero: " + heroes.get(i).getName());
                System.out.println("\t* Life " + heroes.get(i).getLife());
                System.out.println("\t* Strength " + heroes.get(i).getStrength());
                System.out.println("\t* Defence " + heroes.get(i).getDefence());
                System.out.println("\t* Speed " + heroes.get(i).getSpeed());
                System.out.println("\t* Luck " + heroes.get(i).getLuck());
            }
            System.out.println();
            System.out.println("Choose your hero!");
            System.out.println("Select one of the numbers displayed in front of the hero name!");
            int choice = sc.nextInt();
            currentHero = heroes.get(choice - 1);
        } catch (InputMismatchException e) {
            System.out.println("Please choose only numbers!");
            sc = new Scanner(System.in);
            setHero();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please choose only one of the numbers displayed above!");
            sc = new Scanner(System.in);
            setHero();
        }
    }

    private void setBeast() {
        /*
         *In case we add more beasts, this method will randomly return one of the beasts!
         */
        currentBeast = beasts.get(random.nextInt(beasts.size()));
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

    private boolean defend(int luck) {
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
            System.out.println(currentHero.getName() + " grabs his sword and attacks!");
            int initialStrength = currentHero.getStrength();
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
                if (specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE)
                        && specialAbility.isActive()) {
                    currentHero.setStrength(currentHero.getStrength() * 2);
                }
            }
            if (defend(currentBeast.getLuck())) {
                System.out.println(currentBeast.getName() + " dodges the attack!");
                damage = 0;
            } else {
                damage = initiateAttack(currentHero.getStrength(), currentBeast.getDefence());
            }
            currentBeast.setLife(currentBeast.getLife() - damage);
            currentHero.setStrength(initialStrength);
        } else {
            System.out.println(currentBeast.getName() + " rapidly charges our hero and attacks!");
            if (defend(currentHero.getLuck())) {
                damage = 0;
                System.out.println(currentHero.getName() + " dodges the attack!");
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
        System.out.println();
        System.out.println("\t*** GAME STATS ***");
        System.out.println(currentHero.getName() + "'s life: " + currentHero.getLife());
        System.out.println(currentHero.getName() + "'s strength: " + currentHero.getStrength());
        System.out.println(currentHero.getName() + "'s defence: " + currentHero.getDefence());
        System.out.println(currentHero.getName() + "'s speed: " + currentHero.getSpeed());
        System.out.println(currentHero.getName() + "'s luck: " + currentHero.getLuck());
        System.out.println();
        System.out.println(currentBeast.getName() + "'s life: " + currentBeast.getLife());
        System.out.println(currentBeast.getName() + "'s strength " + currentBeast.getStrength());
        System.out.println(currentBeast.getName() + "'s defence " + currentBeast.getDefence());
        System.out.println(currentBeast.getName() + "'s speed " + currentBeast.getSpeed());
        System.out.println(currentBeast.getName() + "'s luck " + currentBeast.getLuck());
        System.out.println("\t*** GAME STATS ***");
        System.out.println();
    }

    private void printRoundStatistics(int damage) {
        /*
         *TODO
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            if (specialAbility.isActive() &&
                    specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE) &&
                    turn) {
                System.out.println("***" + specialAbility.getName() + " is activated this round!");
                System.out.println("***" + specialAbility.getDescription());
            } else if (specialAbility.isActive() &&
                    specialAbility.getAbilityType().equals(AbilityType.DEFENCE_INCREASE) &&
                    !turn) {
                System.out.println("***" + specialAbility.getName() + " is activated this round!");
                System.out.println("***" + specialAbility.getDescription());
            }
        }
        if (turn) {
            System.out.println(currentBeast.getName() + " suffers a damage of " + damage + "!");
        } else {
            System.out.println(currentHero.getName() + " suffers a damage of " + damage + "!");
        }
        System.out.println(currentHero.getName() + "'s remaining life is " + currentHero.getLife() + "!");
        System.out.println(currentBeast.getName() + "'s remaining life is " + currentBeast.getLife() + "!");
        System.out.println("Round " + round + " ended!");
    }

    private void printFirstAttacker() {
        if (turn && round == 1) {
            System.out.println("***" + currentHero.getName() + " is faster than " + currentBeast.getName() + " and strikes first!***");
        } else if (!turn && round == 1) {
            System.out.println("***" + currentBeast.getName() + " is faster than " + currentHero.getName() + " and strikes first!***");
        }
    }

    private void retreatFromBattle() {
        System.out.println("Choose YES/NO");
        String answer = sc.next().toUpperCase();
        switch (answer) {
            case "YES":
                gameOn = true;
                break;
            case "NO":
                System.out.println("CHICKEN!!!!");
                gameOn = false;
                break;
            default:
                retreatFromBattle();
        }
    }

    private void printIntroduction() {
        System.out.println("___________________________________");
        System.out.println("| Welcome to The Brave Hero game! |");
        System.out.println("___________________________________");
        System.out.println("The sun has raised, another day has started!");
        System.out.println("Another adventure awaits just around the corner...");
        System.out.println("Which hero will depart into the Magic Forest to fight with unbelievable monsters?");
    }

    private void printFightIntroduction() {
        System.out.println("Good, " + currentHero.getName() + ", a great choice!");
        System.out.println("" + currentHero.getName() + " left the comfort of his home and started adventuring into the Magic Forest!");
        System.out.println("Not much after, our hero steps right into an ambush! ");
        System.out.println("A terrible monster was waiting for the next victim!");
        System.out.println("But " + currentHero.getName() + " it's not an ordinary human...but the greatest hero of all times!");
    }

    private void printVictory() {
        System.out.println("***VICTORY!***");
        System.out.println(currentHero.getName() + ", is victorious again!");
        System.out.println("No beast is a match for our brave hero!");
    }

    private void printDefeat() {
        System.out.println("***DEFEAT***");
        System.out.println("Unfortunately the beast was too powerful and " + currentHero.getName() + " flee away in pain.");
        System.out.println("Surely, after recovering " + currentHero.getName() + " will send the beast right away from where it come!");
        System.out.println("But this adventure is for another day!");
    }

    private void printTie() {
        System.out.println("***TIE");
        System.out.println("Seems like this beast is truly a match for " + currentHero.getName() + "!");
        System.out.println("Unfortunately, this fight is over, it has to have end!");
    }
}
