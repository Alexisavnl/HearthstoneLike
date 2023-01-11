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
        this.specialAttributeDescription();
        List<Entity> targetList = new ArrayList<>(opponent.getCardsOnTheBoard());
        targetList.add(opponent);
        Entity entity;
        if(player.getName() == "Your tower") {
            OptionsMenu<Entity> cardBot = new OptionsMenu<>("Which card do you want to target ?", targetList);
            entity = cardBot.ask();
        } else {
            Random r = new Random();
            //todo check random
            entity = targetList.get(r.nextInt(targetList.size()+1));
        }
        entity.appliesDamage(1);
    }

}
