package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;
import view.OptionsMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireBall extends Card implements Serializable {

    public FireBall() {
        super("Fireball", -999, 6, TypeOfTribe.FIREBALL,4);
    }

    @Override
    public void specialAttributeDescription() {
        System.out.println("Special attack description: Deal 6 damage.");
    }

    @Override
    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
        List<Entity> targetList = new ArrayList<>(opponent.getCardsOnTheBoard());
        targetList.add(opponent);
        Entity entity;
        if(player.getName() == "Your tower") {
            OptionsMenu<Entity> cardBot = new OptionsMenu<>("\n\033[96mPick the card you want to use to attack:\n" +
                    "----------------------------------\033[0m", targetList);
            entity = cardBot.ask();
        } else {
            Random r = new Random();
            //todo check random
            entity = targetList.get(r.nextInt(targetList.size()));
        }
        entity.appliesDamage(this.getAtk());
    }

    @Override
    public String toString() {
        return this.getName() + " ("+ this.getAtk() + " ATK and cost " +this.getPriceMana() + " mana)";
    }

}
