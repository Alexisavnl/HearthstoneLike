package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;
import view.OptionsMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FireBall extends Card implements Serializable {

    public FireBall() {
        super("Fireball", 0, 6, TypeOfTribe.FIREBALL,4);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Special attack description: Deal 6 damage.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        System.out.println("efw");
        List<Entity> targetList = new ArrayList<>(opponent.getCardsOnTheBoard());
        targetList.add(opponent);
        this.specialAttributeDescription();
        OptionsMenu<Entity> cardBot = new OptionsMenu<>("Which card do you want to target ?", targetList);
        Entity entity = cardBot.ask();
        entity.appliesDamage(this.getAtk());
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
