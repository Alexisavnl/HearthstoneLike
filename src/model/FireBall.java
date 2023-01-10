package model;

public class FireBall extends Card {

    public FireBall(String name, int hp, int atk, int priceMana) {
        super(name, hp, atk, TypeOfTribe.FIREBALL, priceMana);
    }
    public FireBall(String name) {
        this(name, 0, 6, 3);
    }

    public void specialAttributeDescription() {
        System.out.println("This card is a Nurse type card if you use its special attack defensive it will heal the card you choose by 3 hp.");
    }

    public short applySpecialAttack() {

    }

    @Override
    public void fight(Card targetCard, Player opponent) {

    }

    @Override
    public void fight(Player opponent) {

    }
}
