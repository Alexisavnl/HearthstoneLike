package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class Blizzard extends Card implements Serializable {

    public Blizzard() {
        super("Blizzard", -999, 2, TypeOfTribe.BLIZZARD,6);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Deal 2 damage to all enemy minions and Freeze them.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
        for(Card c: opponent.getCardsOnTheBoard()) {
            c.setCanPlay(false);
            c.appliesDamage(this.getAtk());
        }
    }

    @Override
    public String toString() {
        return this.getName() + " ("+ this.getAtk() + " ATK and cost " +this.getPriceMana() + " mana)";
    }

}
