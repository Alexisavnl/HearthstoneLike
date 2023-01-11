package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class MurlocTidehunter extends Card implements Serializable {

    public MurlocTidehunter() {
        super("Murloc Tidehunter", 1,2,TypeOfTribe.MURLOCTIDEHUNTER,2);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Special attack description: Battlecry: Summon a 1/1 Murloc Scout.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
        player.getCardsOnTheBoard().add(new MurlocScout());
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
