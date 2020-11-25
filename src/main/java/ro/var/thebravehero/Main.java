package ro.var.thebravehero;

import ro.var.thebravehero.data.GetCharacters;
import ro.var.thebravehero.models.MagicForest;

import java.util.Scanner;

/*
 *The main entry point of the program
 *Here, the MagicForest object (the class that contains the entire game logic) is created and the parameters are populated using GetCharacters class
 *Everything is happening in a do-while loop so the player can start a new game everytime wants
 */


public class Main {

    public static void main(String[] args) {
        do {
            MagicForest magicForest = new MagicForest(GetCharacters.getHeroes(), GetCharacters.getBeasts());
            magicForest.startGame();
        } while (playAgain());
    }

    private static boolean playAgain() {
        /*
         *Simple method added to allow the player to start a new game
         *It returns a boolean based on the player's answer after the answer is checked if it matches one of the allowed cases
         *If the answer cannot be matched with one of the cases, it loops until a good answer is provided
         */

        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to start another adventure? YES/NO");
        String answer = sc.next().toUpperCase();
        switch (answer) {
            case "YES":
                return true;
            case "NO":
                System.out.println("Thank you for playing!");
                System.exit(0);
                break;
            default:
                System.out.println("Unfortunately... we could not match your answer! Let's try again!");
                playAgain();
        }
        return false;
    }
}
