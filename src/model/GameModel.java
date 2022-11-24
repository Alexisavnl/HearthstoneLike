package model;

import view.DifficultyMode;
import view.OptionsMenu;

import java.io.Serializable;
import java.util.*;

public class GameModel implements Serializable {
    private Log theLog;
    private Player thePlayer;
    private Player botPlayer;

    private List<Card> pileOfCard;

    private DifficultyMode difficultyMode;
    public GameModel(DifficultyMode difficultyMode) {
        theLog = new Log();
        this.difficultyMode = difficultyMode;
        pileOfCard = new ArrayList<>() {
            {
                add(new Nurse("test1"));
                add(new Nurse("test2"));
                add(new Nurse("test3"));
                add(new Nurse("test4"));
                add(new Nurse("test5"));
                add(new Nurse("test6"));
                add(new Nurse("test7"));
                add(new Nurse("test8"));
                add(new Nurse("test9"));
                add(new Nurse("test10"));
                add(new Nurse("test11"));
                add(new Nurse("test12"));
                add(new Nurse("test13"));
                add(new Nurse("test14"));
                add(new Nurse("test15"));
                add(new Nurse("test16"));
                add(new Nurse("test17"));
                add(new Nurse("test18"));
            }
        };
        Collections.shuffle(pileOfCard);
        switch (difficultyMode) {
            case easy:
                botPlayer = new Player(15, new ArrayList<>(), "The bot");
                break;
            case medium:
                botPlayer = new Player(20, new ArrayList<>(), "The bot");
                break;
            case hard:
                botPlayer = new Player(25, new ArrayList<>(), "The bot");
                break;
        }
        thePlayer = new Player(20, new ArrayList<>(), "You");
        this.takeCards(thePlayer, 3);
        this.takeCards(botPlayer, 3);
    }
    public Log getLog() {
        return theLog;
    }

    public Player getPlayer() {
        return this.thePlayer;
    }

    public Player getBotPlayer() {
        return this.botPlayer;
    }

    public boolean endOfTheGame() {
        return !thePlayer.isAlive() || !botPlayer.isAlive();
    }

    public void fight() {
        OptionsMenu<Card> cardAvailable = new OptionsMenu<Card>("Choose a card to attak:", thePlayer.getCards());
        Card cardChosenToAttack = cardAvailable.ask();
        OptionsMenu<Card> cardAvailableBot = new OptionsMenu<Card>("Which card are you attacking:", botPlayer.getCards());
        Card targetCard = cardAvailableBot.ask();
        Card cardChosenByBot = null;
        Card targetCardBot = null;
        switch (this.difficultyMode){
            case easy, medium:
                Random r = new Random();
                int ramdonNumber = r.nextInt(botPlayer.getCards().size());
                cardChosenByBot = botPlayer.getCards().get(ramdonNumber);
                ramdonNumber = r.nextInt(thePlayer.getCards().size());
                targetCardBot = thePlayer.getCards().get(ramdonNumber);
                break;
            case hard:
                cardChosenByBot = strongestCard(botPlayer.getCards());
                targetCardBot = weakestCard(thePlayer.getCards());
                break;
        }



        if(attack(cardChosenToAttack, targetCard, thePlayer, botPlayer)) return;
        if(attack(cardChosenByBot, targetCardBot, botPlayer, thePlayer)) return;


        thePlayer.incGoldAndManaEachTurns();
        theLog.addEntry("Your gold is now at " + thePlayer.getGold());
        theLog.addEntry("Your mana is now at " + thePlayer.getMana());
    }


    public void printGameBoard() {
        theLog.addEntry(botPlayer.toString());
        theLog.addEntry(thePlayer.toString());
    }

    public void takeACard(Player player) {
        if(pileOfCard.size() != 0){
            if(player.getCards().size() < 4 && player.getMana() >= 3) {
                player.setMana((player.getMana() - 3));
                player.addCard(pileOfCard.remove(0));
                theLog.addEntry(player.getName() + " picked the card: " +player.getCards().get(player.getCards().size() -1).toString());
            } else {
                if (player.getCards().size() >= 4) {
                    theLog.addEntry("You have reached the maximum number of cards on the ground");
                } else {
                    theLog.addEntry("You have only " + player.getMana() + " mana. It takes 3 mana to buy a card");
                }
            }
        } else {
            theLog.addEntry("The pile is empty");
        }
    }

    public void takeCards(Player player, int numberOfCards) {
        for(int i = 0; i < numberOfCards; i++) {
            player.addCard(pileOfCard.remove(0));
        }
    }

    public void verifyLifeCard(Card choosenCard,Card targetCard, Player attacker, Player attacked) {
        if(choosenCard.attack() >= targetCard.getHP()){
            theLog.addEntry("Well played "+ attacker.getName() +"! You kill the card");
            if(choosenCard.attack() - targetCard.getHP() > 0 ) {
                attacked.applyDamages(choosenCard.attack() - targetCard.getHP());
                theLog.addEntry( attacker.getName() + " managed to inflict direct damage to your opponent");
            }
        }
    }
    public boolean attack(Card choosenCard,Card targetCard, Player attacker, Player attacked) {
        verifyLifeCard(  choosenCard, targetCard,  attacker, attacked);
        attacked.applyDamagesOnACard(choosenCard.attack(), targetCard);
        if (!attacked.isAlive()) {
            theLog.addEntry( attacked +" dies!");
            return true;
        } else {
            return false;
        }
    }

    public void upgradeCard() {
        if (thePlayer.getGold() >= 10){
            OptionsMenu<Card> cardAvailable = new OptionsMenu<Card>("Choose a card to upgrade:", thePlayer.getCards());
            Card cardChosenToUpgrade = cardAvailable.ask();
            if(cardChosenToUpgrade.getCountUpgrade() >= 4){
                theLog.addEntry("The card is already upgraded to the maximum.");
            } else {
                cardChosenToUpgrade.upgradeCard();
                cardChosenToUpgrade.setCountUpgrade(cardChosenToUpgrade.getCountUpgrade()+1);
            }
        } else {
            theLog.addEntry("You don't have enough gold to upgrade your card. It takes 10 gold to upgrade a card.");
        }

    }

    private Card strongestCard(List<Card> cards) {
        Card strongestCard = cards.get(0);
        for (Card c: cards){
            if (c.attack() > strongestCard.attack()) {
                strongestCard = c;
            }
        }
        return  strongestCard;
    }

    private Card weakestCard(List<Card> cards) {
        Card weakestCard = cards.get(0);
        for (Card c: cards){
            if (c.getHP() < weakestCard.getHP()) {
                weakestCard = c;
            }
        }
        return  weakestCard;
    }
}
