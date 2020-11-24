package ro.var.thebravehero;

import ro.var.thebravehero.data.GetCharacters;
import ro.var.thebravehero.model.MagicForest;

public class Main {
    public static void main (String [] args){
        MagicForest magicForest = new MagicForest(GetCharacters.getHeroes(),GetCharacters.getBeasts());
        magicForest.initiateGame();
    }
}
