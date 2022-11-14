package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel implements Serializable {
    private Log theLog;
    private Player thePlayer;
    private Player botPlayer;

    private List<Card> pileOfCard;

    public GameModel() {
        theLog = new Log();
        pileOfCard = new ArrayList<Card>(){
            {
                add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
                add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
                add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
                add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
                add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
                add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
                add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
                add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
            }
        };
        Collections.shuffle(pileOfCard);
        thePlayer = new Player(12, new ArrayList<Card>(){{         add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
        add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
        add(new Nurse("test", 12,2,TypeOfTribe.NURSE));}});
        botPlayer = new Player(12,  new ArrayList<Card>(){{         add(new Nurse("test", 12,2,TypeOfTribe.NURSE));
            add(new Nurse("test2", 8,2,TypeOfTribe.NURSE));
            add(new Nurse("test", 12,2,TypeOfTribe.NURSE));}});
    }
    public Log getLog() {
        return theLog;
    }

    public Player getPlayer() {
        return thePlayer;
    }

    public boolean endOfTheGame() {
        return !thePlayer.isAlive() || !botPlayer.isAlive();
    }

    public void fight() {
        thePlayer.applyDamages(botPlayer.getCards().get(0).attack());
        theLog.addEntry("Enemy hits player with " + botPlayer.getCards().get(0).attack() + " damages");
        botPlayer.applyDamages( thePlayer.getCards().get(0).attack());
        theLog.addEntry("Player hits enemy with " + thePlayer.getCards().get(0).attack() + " damages");
        if (!botPlayer.isAlive()) {
            theLog.addEntry("Enemy dies!");
            return;
        }
        if (!thePlayer.isAlive()) {
            theLog.addEntry("Player dies!");
        }
    }

    public void takeACard(Player player) throws Exception {
        if(player.getCards().size() < 4) {
            player.addCard(pileOfCard.remove(0));
        } else {
            throw new Exception("You have already reached the maximum number of cards on the board");
        }
    }

    public void takeCards(Player player, int numberOfCards) throws Exception {
        for(int i = 0; i < numberOfCards; i++) {
            takeACard(player);
        }
    }
}
