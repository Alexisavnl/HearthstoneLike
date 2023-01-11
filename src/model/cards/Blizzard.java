package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class Blizzard extends Card implements Serializable {

    public Blizzard(String name) {
        super("Blizzard", 0, 2, TypeOfTribe.ELVENARCHER,6);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Deal 2 damage to all enemy minions and Freeze them.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
        for(Card c: opponent.getCardsOnTheBoard()) {
            c.setPlayed(true);
            c.appliesDamage(this.getAtk());
        }
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
