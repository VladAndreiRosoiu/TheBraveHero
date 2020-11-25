package ro.var.thebravehero.models.characters;

/*
 *The parent class for Hero and Beats, as both the before mentioned classes have shared attributes
 *This class is abstract so that it cannot be instantiated
 *The attributes are self explanatory, getters and setters for all of them
 */


public abstract class Character {
    private String name;
    private int life;
    private int strength;
    private int defence;
    private int speed;
    private int luck;

    public Character(String name, int life, int strength, int defence, int speed, int luck) {
        this.name = name;
        this.life = life;
        this.strength = strength;
        this.defence = defence;
        this.speed = speed;
        this.luck = luck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

}
