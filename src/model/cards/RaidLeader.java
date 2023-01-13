package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class RaidLeader extends Card implements Serializable {

    public RaidLeader() {
        super("Raid Leader", 3,2,TypeOfTribe.RAIDLEADER,3);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Special attack description: Your other minions have +1 Attack.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
        for(Card c: player.getCardsOnTheBoard()) {
            c.setAtk(c.getAtk()+1);
        }
    }

    public void removeEffect(Player player){
        for(Card c: player.getCardsOnTheBoard()) {
            c.setAtk(c.getAtk()-1);
        }
    }

}
