package ro.var.thebravehero;

import ro.var.thebravehero.data.GetCharacters;
import ro.var.thebravehero.models.MagicForest;

import java.util.Scanner;

/*
 *TODO
 */


public class Main {

    public static void main(String[] args) {
        do {
            MagicForest magicForest = new MagicForest(GetCharacters.getHeroes(), GetCharacters.getBeasts());
            magicForest.startGame();
        } while (playAgain());
    }

    private static boolean playAgain() {
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
                System.out.println("Oooops.. we could not match your answer. Let's try again!");
                playAgain();
        }
        return false;
    }
}
