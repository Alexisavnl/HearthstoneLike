package model;

public class Kamikaze extends Card{
    public Kamikaze(String name, int hp, int atk, int priceMana) {
        super(name, hp, atk, TypeOfTribe.KAMIKAZE, priceMana );
    }
    public Kamikaze(String name) {
        super(name, 5, 4, TypeOfTribe.KAMIKAZE,3);
    }
    public void specialAttributeDescription() {
        System.out.println("This card is a Kamikaze type card if you use its special attack defensive it will deal 3dmg to each cards of the bot but kill himself");
    }

    public short applySpecialAttack() {
        return 3;
    }
}
