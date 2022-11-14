package model;

public class Nurse extends Card {

    public Nurse(String name, int hp, int atk, TypeOfTribe tribeName) {
        super(name, hp, atk, tribeName);
    }

    public void specialAttributeDescription() {
        System.out.println("This card is a Nurse type card if you use its special attack defensive it will heal the card you choose by 3 hp.");
    }

    public short applySpecialAttack() {
        return 3;
    }
}
