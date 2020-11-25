package ro.var.thebravehero.data;

import ro.var.thebravehero.models.abilities.AbilityType;
import ro.var.thebravehero.models.abilities.SpecialAbility;
import ro.var.thebravehero.models.characters.Beast;
import ro.var.thebravehero.models.characters.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 *Although hard coding data in the app is not a good approach, file reading or database was avoided (no additional frameworks are allowed)
 *In the future, this class may be replaced with DAO and all the data can be stored in an database
 */
public class GetCharacters {

    private static final Random random = new Random();

    public static List<Hero> getHeroes() { // this method returns a list of heroes
        List<SpecialAbility> specialAbilities = new ArrayList<>();
        SpecialAbility specialAbility1 = new SpecialAbility(
                "Dragon's Force",
                10,
                "When activated, doubles hero power",
                AbilityType.DAMAGE_INCREASE,
                false);
        SpecialAbility specialAbility2 = new SpecialAbility("Magic Shield",
                20,
                "When activated, next attack damage will be reduced by half",
                AbilityType.DEFENCE_INCREASE,
                false);
        specialAbilities.add(specialAbility1);
        specialAbilities.add(specialAbility2);
        Hero hero = new Hero("Carl",
                (65 + random.nextInt(95 - 65 + 1)),
                (60 + random.nextInt(70 - 60 + 1)),
                (40 + random.nextInt(50 - 40 + 1)),
                (40 + random.nextInt(50 - 40 + 1)),
                (10 + random.nextInt(30 - 10 + 1)),
                specialAbilities);
        return Arrays.asList(hero);
    }

    public static List<Beast> getBeasts() { // this method returns a list of beasts
        Beast beast = new Beast("Beast",
                (55 + random.nextInt(80 - 55 + 1)),
                (50 + random.nextInt(80 - 50 + 1)),
                (35 + random.nextInt(55 - 35 + 1)),
                (40 + random.nextInt(60 - 40 + 1)),
                (25 + random.nextInt(40 - 25 + 1)));
        return Arrays.asList(beast);
    }
}
