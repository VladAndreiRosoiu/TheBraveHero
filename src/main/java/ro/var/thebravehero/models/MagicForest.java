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
 *This class is responsible for the entire game logic
 *To future-proof the app, MagicForest requires a List of heroes and a List of beasts
 *In this way, later on, new heroes and beasts can be added without modifying anything
 */

public class MagicForest {

    private Scanner sc = new Scanner(System.in);
    private final List<Hero> heroes;        // List of heroes needed for the app logic
    private final List<Beast> beasts;       // List of beasts needed for the app logic
    private Hero currentHero;               // after choosing a hero from the List<Hero> heroes, this variable points to that selected hero
    private Beast currentBeast;             // after randomizing a beast from the List<Beast> beasts, this variable points to that selected beast
    private int round = 1;                  // counter used to track rounds
    private final Random random = new Random();
    private boolean turn;                   // flag used to keep track of the current attacker. In this way, hero and beast switch places one after the other
    private boolean gameOn;                 // flag used to keep track of the player's wish to continue the game or to end it

    public MagicForest(List<Hero> heroes, List<Beast> beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
    }

    public void startGame() {
      /*
      *Method created to link the app logic
       */
        printGameIntroduction();            // print method
        setHero();                          // hero selection method
        setBeast();                         // random beast selection method
        printFightIntroduction();           // print method
        turn = isHeroFirst();               // setting the flag used to keep track of the turns. True = hero first, false = beast first
        printInitialStats();                // print method - stats of characters
        System.out.println("Are you ready start the fight?");
        retreatFromBattle();                // method that allows the player to start the fight or not
        printFirstAttacker();               // print method - who goes first
        while (gameOn && round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0) {
            System.out.println("Round " + round + " starts!");
            setHeroAbilities();             // at the beginning of each round, hero abilities are activated by their given chance
            int damage = playRound();       // method used to handle the attack/defend part of the game after
            printRoundStatistics(damage);   // print method
            resetHeroAbilities();           // when the round finishes, hero abilities are deactivated
            turn = !turn;                   // after a round has ended, "turn" takes it's opposite to allow the other character to attack
            round++;                        // round is incremented at the end
            if (round < 21 && currentHero.getLife() > 0 && currentBeast.getLife() > 0) { // if statement introduced so that the
                                                                                         // player is asked to continue only if ending
                                                                                         // game conditions are not met
                System.out.println("Continue?");
                retreatFromBattle();
            } else {
                break;
            }
        }
        if (currentHero.getLife() <= 0) {   // if statement introduced to handle the end game situations
            printDefeat();                  // print method
        } else if (currentBeast.getLife() <= 0) {
            printVictory();                 // print method
        } else if (round > 20) {
            printTie();                     // print method
        }
        round = 1;                          // when the game ends, round counter is reset back to 1
    }

    private void setHero() {
        /*
         *In case we add more heroes, this method allows the player to select one of them
         *Also, it validates user input and loops until a valid answer is provided by the user
         */
        try {
            for (int i = 0; i < heroes.size(); i++) {
                System.out.println(i + 1 + " - Hero: " + heroes.get(i).getName());
                System.out.println("\t* Life " + heroes.get(i).getLife());
                System.out.println("\t* Strength " + heroes.get(i).getStrength());
                System.out.println("\t* Defence " + heroes.get(i).getDefence());
                System.out.println("\t* Speed " + heroes.get(i).getSpeed());
                System.out.println("\t* Luck " + heroes.get(i).getLuck());
            }
            System.out.println("Choose your hero by typing the corresponding number!");
            int choice = sc.nextInt();
            currentHero = heroes.get(choice - 1);
        } catch (InputMismatchException e) {
            System.out.println("Please choose only numbers!");
            sc = new Scanner(System.in);
            setHero();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please choose only one of the numbers displayed!");
            sc = new Scanner(System.in);
            setHero();
        }
    }

    private void setBeast() {
        /*
         *In case we add more beasts, this method randomly returns one of the beasts
         */
        currentBeast = beasts.get(random.nextInt(beasts.size()));
    }

    private boolean isHeroFirst() {
        /*
         *This method checks the criteria to decide who goes first
         *The rule is : the character with greater speed goes first. If their speeds are equal, the character with the highest luck goes first
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
         *This method activates hero special abilities based on the activation percentage of each ability
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            if ((1 + random.nextInt(100)) <= specialAbility.getActivationChance()) {
                specialAbility.setActive(true);
            }
        }
    }

    private void resetHeroAbilities() {
        /*
         *At the end of the round, this method is used to deactivate hero abilities
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            specialAbility.setActive(false);
        }
    }

    private int initiateAttack(int strength, int defence) {
        /*
         *This method is used to calculate the damage amount
         *The rule is : damage done is equal to strength of the attacked minus defenders defence. If the amount is between 0 and 100, the amount is returned
         *If damage is bellow 0, will return 0
         *If damage is higher than 100, will return 100
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
         *In each round, the defender has a chance to dodge the attack
         *If the attacked will be dodged or not, it's based on the characters luck percentage
         */
        return (1 + random.nextInt(100) <= luck);
    }

    private int playRound() {
        /*
         *This method is responsible for handling attack, defence and special abilities of the hero
         *It's based on an if-else statement and it's using the flag "turn"
         *If turn is true, the hero logic is applied, else, the beast logic is applied
         */
        int damage;
        if (turn) {
            System.out.println(currentHero.getName() + " grabs his sword and attacks!");
            int initialStrength = currentHero.getStrength();    // when hero is attacking it is a chance that an offensive special abilities activates
                                                                // this variable is used to store the initial strength of the hero and re-apply it when the attack finishes
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
                if (specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE)
                        && specialAbility.isActive()) {
                    currentHero.setStrength(currentHero.getStrength() * 2);
                }
            }
            if (defend(currentBeast.getLuck())) {               // if statement used to validate the dodge logic
                System.out.println(currentBeast.getName() + " dodges the attack!");
                damage = 0;
            } else {
                damage = initiateAttack(currentHero.getStrength(), currentBeast.getDefence());
            }
            currentBeast.setLife(currentBeast.getLife() - damage);
            currentHero.setStrength(initialStrength);
        } else {
            System.out.println(currentBeast.getName() + " rapidly charges our hero and attacks!");
            if (defend(currentHero.getLuck())) { // if statement used to validate the dodge logic
                damage = 0;
                System.out.println(currentHero.getName() + " dodges the attack!");
            } else {
                damage = initiateAttack(currentBeast.getStrength(), currentHero.getDefence());
            }
            for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) { // when the hero is attacked, it has a chance to activate an defensive ability
                if (specialAbility.getAbilityType().equals(AbilityType.DEFENCE_INCREASE)
                        && specialAbility.isActive()) {
                    damage = damage / 2; // dividing an integer is not the best approach, but for this game is ok
                }
            }
            currentHero.setLife(currentHero.getLife() - damage);
        }
        return damage;
    }

    private void retreatFromBattle() {
        /*
         *This method is used to break the while loop used in startGame() and ask the player if wants to continue game or to retreat
         */
        System.out.println("Type YES/NO");
        String answer = sc.next().toUpperCase();
        switch (answer) {
            case "YES":
                gameOn = true;
                break;
            case "NO":
                printDefeat();
                gameOn = false;
                break;
            default:
                System.out.println("Unfortunately... we could not match your answer! Let's try again!");
                retreatFromBattle();
        }
    }

    private void printInitialStats() {
        /*
         *Printing method  - prints characters stats
         */
        System.out.println("\t*** GAME STATS ***");
        System.out.println("# " + currentHero.getName() + "'s life: " + currentHero.getLife());
        System.out.println("# " + currentHero.getName() + "'s strength: " + currentHero.getStrength());
        System.out.println("# " + currentHero.getName() + "'s defence: " + currentHero.getDefence());
        System.out.println("# " + currentHero.getName() + "'s speed: " + currentHero.getSpeed());
        System.out.println("# " + currentHero.getName() + "'s luck: " + currentHero.getLuck());
        System.out.println();
        System.out.println("# " + currentBeast.getName() + "'s life: " + currentBeast.getLife());
        System.out.println("# " + currentBeast.getName() + "'s strength: " + currentBeast.getStrength());
        System.out.println("# " + currentBeast.getName() + "'s defence: " + currentBeast.getDefence());
        System.out.println("# " + currentBeast.getName() + "'s speed: " + currentBeast.getSpeed());
        System.out.println("# " + currentBeast.getName() + "'s luck: " + currentBeast.getLuck());
        System.out.println("\t*** GAME STATS ***");
    }

    private void printRoundStatistics(int damage) {
        /*
         *Print method - prints round stats
         */
        for (SpecialAbility specialAbility : currentHero.getSpecialAbilities()) {
            if (specialAbility.isActive() &&
                    specialAbility.getAbilityType().equals(AbilityType.DAMAGE_INCREASE) &&
                    turn) {
                System.out.println("*** " + specialAbility.getName() + " is active this round!");
                System.out.println("*** " + specialAbility.getDescription() + "!");
            } else if (specialAbility.isActive() &&
                    specialAbility.getAbilityType().equals(AbilityType.DEFENCE_INCREASE) &&
                    !turn) {
                System.out.println("*** " + specialAbility.getName() + " is active this round!");
                System.out.println("*** " + specialAbility.getDescription() + "!");
            }
        }
        if (turn) {
            System.out.println("# " + currentBeast.getName() + " suffers a damage of " + damage + "!");
        } else {
            System.out.println("# " + currentHero.getName() + " suffers a damage of " + damage + "!");
        }
        System.out.println("# " + currentHero.getName() + "'s remaining life is " + currentHero.getLife() + "!");
        System.out.println("# " + currentBeast.getName() + "'s remaining life is " + currentBeast.getLife() + "!");
        System.out.println("Round " + round + " ends!");
    }

    private void printFirstAttacker() {
        /*
         *Print method - prints who will attack first
         */
        if (turn && round == 1) {
            System.out.println("***" + currentHero.getName() + " is faster than " + currentBeast.getName() + " and strikes first!***");
        } else if (!turn && round == 1) {
            System.out.println("***" + currentBeast.getName() + " is faster than " + currentHero.getName() + " and strikes first!***");
        }
    }

    private void printGameIntroduction() {
        /*
         *Print method - prints game introduction
         */
        System.out.println("___________________________________");
        System.out.println("| Welcome to The Brave Hero game! |");
        System.out.println("___________________________________");
        System.out.println("The sun is rising, a beautiful day starts!");
        System.out.println("Another adventure awaits just around the corner...");
        System.out.println("Which hero will depart into the Magic Forest to fight with unbelievable monsters?");
    }

    private void printFightIntroduction() {
        /*
         *Print method - prints hero details
         */
        System.out.println("Good, " + currentHero.getName() + ", a great choice!");
        System.out.println("" + currentHero.getName() + " left the comfort of his home and started adventuring into the Magic Forest!");
        System.out.println("Not much after, our hero steps right into an ambush! ");
        System.out.println("A terrible monster was waiting for the next victim!");
        System.out.println("But " + currentHero.getName() + "'s not an ordinary human...but the greatest hero of all times!");
    }

    private void printVictory() {
        /*
         *Print method - used if the hero is victorious
         */
        System.out.println("***VICTORY!***");
        System.out.println(currentHero.getName() + ", is victorious again!");
        System.out.println("No beast is a match for our brave hero!");
    }

    private void printDefeat() {
        /*
         *Print method - used if the hero is defeated
         */
        System.out.println("***DEFEAT***");
        System.out.println("Unfortunately the beast was too powerful and " + currentHero.getName() + " flee away in pain.");
        System.out.println("Surely, after recovering " + currentHero.getName() + " will send the beast right away from where it come!");
        System.out.println("But this adventure is for another day!");
    }

    private void printTie() {
        /*
         *Print method - used if the round counter reaches 20
         */
        System.out.println("***TIE");
        System.out.println("Seems like this beast is truly a match for " + currentHero.getName() + "!");
        System.out.println("Unfortunately, this fight is over, it has to have end!");
    }
}
