package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;
import view.OptionsMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElvenArcher extends Card implements Serializable {

    public ElvenArcher() {
        super("Elven Archer", 1, 1, TypeOfTribe.ELVENARCHER,1);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Special attack description: Battlecry: Deal 1 damage.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        List<Entity> targetList = new ArrayList<>(opponent.getCardsOnTheBoard());
        targetList.add(opponent);
        this.specialAttributeDescription();
        OptionsMenu<Entity> cardBot = new OptionsMenu<>("Which card do you want to target ?", targetList);
        Entity entity = cardBot.ask();
        entity.appliesDamage(1);
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
