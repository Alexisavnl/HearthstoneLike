package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class FireBall extends Card implements Serializable {

    public FireBall(String name, int hp, int atk, int priceMana) {
        super(name, hp, atk, TypeOfTribe.FIREBALL, priceMana);
    }
    public FireBall(String name) {
        this(name, 0, 6, 1);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("This card is a Nurse type card if you use its special attack defensive it will heal the card you choose by 3 hp.");
    }

    @Override
    public short applySpecialAttack() {

        return 0;
    }

    @Override
    public void fight(Card card, Player opponent) {
        card.appliesDamage(this.getAtk());
    }

    @Override
    public void fight(Player opponent) {
        opponent.appliesDamage(this.getAtk());
    }

    @Override
    public void fight(Entity entity){
        entity.appliesDamage(this.getAtk());
    }

}
