package model;

import view.DifficultyMode;
import view.OptionsMenu;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class GameModel implements Serializable {

    private Log theLog;
    private Player thePlayer;
    private Player botPlayer;

    private List<Card> pileOfCard;

    private DifficultyMode difficultyMode;

    private int round = 1;

    private boolean isPlayerTurn = true;

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
                botPlayer = new Player(15, new ArrayList<>(), "The bot tower");
                break;
            case medium:
                botPlayer = new Player(20, new ArrayList<>(), "The bot tower");
                break;
            case hard:
                botPlayer = new Player(25, new ArrayList<>(), "The bot tower");
                break;
        }
        thePlayer = new Player(20, new ArrayList<>(), "Your tower");
        this.takeCards(thePlayer, 1);
        this.takeCards(botPlayer, 1);
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

    public void playerFight() {
        int damage;
        if(thePlayer.isPlayed() && thePlayer.getCardsOnTheBoard().size() == 0) {
            theLog.addEntry("No action is possible at this time. Place a card or skip your turn.");
            return;
        }
        if(thePlayer.getCardsOnTheBoard().size() > 0) {
            OptionsMenu<Card> cardAvailablePlayer = new OptionsMenu<>("Which card do you choose:", thePlayer.getCardsOnTheBoard());
            Card cardChoosen = cardAvailablePlayer.ask();
            if(cardChoosen.isJustPlaced() || cardChoosen.isPlayed()) {
                theLog.addEntry("you must wait until the next turn to play the card.");
                return;
            }
            damage = cardChoosen.getAtk();
        } else {
            theLog.addEntry("You have no cards on the board. You will use your tower to attack");
            damage = thePlayer.getAtk();
            thePlayer.setPlayed(true);
        }
        System.out.println(botPlayer.getCardsOnTheBoard());
            List<Entity> possibleActions = new ArrayList<Entity>(botPlayer.getCardsOnTheBoard());
        System.out.println(possibleActions);
            possibleActions.add(botPlayer);
        System.out.println(possibleActions);
            OptionsMenu<Entity> cardAvailableBot = new OptionsMenu<>("Who do you want to attack:",  possibleActions);

            Entity e = cardAvailableBot.ask();
            e.appliesDamage(damage);

    }

    //proccess
    public void botFight() {
        System.out.println("carte dnas la main " + botPlayer.getCardsInHand());
        Collection<Card> collection = botPlayer.getCardsInHand();
        List<Card> cardToPose = collection.stream().filter(i -> {
            if(i.getPriceMana() <= botPlayer.getMana()){
                theLog.addEntry("the bot has placed this card on the board " + i.toString());
                botPlayer.setMana(botPlayer.getMana() - i.getPriceMana());
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        System.out.println("carte selec " + cardToPose.toString());
        botPlayer.getCardsInHand().removeAll(cardToPose);
        System.out.println("after remove "+ botPlayer.getCardsInHand());
        botPlayer.getCardsOnTheBoard().addAll(cardToPose);
        System.out.println(botPlayer.getCardsOnTheBoard().toString());
        List<Entity> botArmy = new ArrayList<Entity>(botPlayer.getCardsOnTheBoard());
        botArmy.add(botPlayer);
        Random r = new Random();
        for(Entity e: botArmy) {
            if (e.isPlayed() == false) {
                if (thePlayer.getCardsOnTheBoard().size() > 0) {
                    int indexTarget = r.nextInt(thePlayer.getCardsOnTheBoard().size());
                    Card targetCard = thePlayer.getCardsOnTheBoard().get(indexTarget);
                    theLog.addEntry("The bot attack this " + targetCard.toString());
                    theLog.addEntry("Your "+ targetCard.getTribeName() + " before attack: "+ targetCard.getHp() + "hp");
                    targetCard.appliesDamage(e.getAtk());
                    theLog.addEntry("Your " + targetCard.getName() + " after attack: "+ targetCard.getHp() + "hp");
                    theLog.addEntry("Bot " + e.getName() + " before attack: "+ e.getHp() + "hp");
                    e.appliesDamage(targetCard.getAtk());
                    theLog.addEntry("Bot " + e.getName() + " after attack: "+ e.getHp() + "hp");
                } else {
                    theLog.addEntry("Bot attack your tower with this " + e.getName());
                    theLog.addEntry("Your tower before attack " + thePlayer.getHp() + "hp");
                    thePlayer.appliesDamage(e.getAtk());
                    theLog.addEntry("Your tower after attack " + thePlayer.getHp() + "hp");
                }
            }
        }
        isPlayerTurn = true;
    }

    public void newRound() {
        this.round++;
        thePlayer.setMana(round);
        botPlayer.setMana(round);
        thePlayer.setPlayed(false);
        botPlayer.setPlayed(false);
        for(Card c: thePlayer.getCardsOnTheBoard()){
            c.setPlayed(false);
            c.setJustPlaced(false);
        }
        for(Card c: botPlayer.getCardsOnTheBoard()){
            c.setPlayed(false);
            c.setJustPlaced(false);
        }
        takeACard(thePlayer);
        takeACard(botPlayer);
    }

    public void playerHasFinishedRound() {
        newRound();
        isPlayerTurn = false;
    }


    public void printGameBoard() {
        theLog.addEntry(botPlayer.toString());
        theLog.addEntry(thePlayer.toString());
    }

    public void takeACard(Player player) {
        if(pileOfCard.size() != 0){
                player.addCardInHand(pileOfCard.remove(0));
                theLog.addEntry(player.getName() + " picked the card: " +player.getCardsInHand().get(player.getCardsInHand().size() -1).toString());
        } else {
            theLog.addEntry("The pile is empty");
        }
    }
    public void putACardOnBoard(Player player) {
        if(player.getCardsInHand().size() > 0){
            if(cheapestCard(player.getCardsInHand()).getPriceMana() <= player.getMana() ) {
                OptionsMenu<Card> cardAvailable = new OptionsMenu<>("Which card do you want to put on the board", thePlayer.getCardsInHand());
                Card cardChosen = cardAvailable.ask();
                if (cardChosen.getPriceMana() >= player.getMana()) {
                    theLog.addEntry("You don't have enough money to deposit this card");
                } else if (!(cardChosen.getPriceMana() >= player.getMana())) {
                    int indexOfCard = player.getCardsInHand().indexOf(cardChosen);
                    Card c = player.getCardsInHand().remove(indexOfCard);
                    c.setJustPlaced(true);
                    player.addCardOnTheBoard(c);
                    player.setMana(player.getMana() - cardChosen.getPriceMana());
                }

            } else {
                theLog.addEntry("You don't have enough money to deposit a card");
            }
        } else {
            theLog.addEntry("you have no cards in your hand");
        }
    }

    public void takeCards(Player player, int numberOfCards) {
        for(int i = 0; i < numberOfCards; i++) {
            player.addCardInHand(pileOfCard.remove(0));
        }
    }

    public void verifyLifeCard(Card choosenCard,Card targetCard, Player attacker, Player attacked) {
        if(choosenCard.getAtk() >= targetCard.getHp()){
            theLog.addEntry("Well played "+ attacker.getName() +"! You kill the card");
            if(choosenCard.getAtk() - targetCard.getHp() > 0 ) {
                attacked.appliesDamage(choosenCard.getAtk() - targetCard.getHp());
                theLog.addEntry( attacker.getName() + " managed to inflict direct damage to your opponent");
            }
        }
    }
    public boolean attack(Card choosenCard,Card targetCard, Player attacker, Player attacked) {
        verifyLifeCard(  choosenCard, targetCard,  attacker, attacked);
        attacked.applyDamagesOnACard(choosenCard.getAtk(), targetCard);
        if (!attacked.isAlive()) {
            theLog.addEntry( attacked +" dies!");
            return true;
        } else {
            return false;
        }
    }

    public void upgradeCard() {
        if (thePlayer.getGold() >= 10){
            OptionsMenu<Card> cardAvailable = new OptionsMenu<Card>("Choose a card to upgrade:", thePlayer.getCardsInHand());
            Card cardChosenToUpgrade = cardAvailable.ask();

            if(cardChosenToUpgrade.getCountUpgrade() >= 4){
                theLog.addEntry("The card is already upgraded to the maximum.");
            } else {
                cardChosenToUpgrade.upgradeCard();
            }
        } else {
            theLog.addEntry("You have only " + thePlayer.getGold() + " gold. It takes 10 gold to upgrade a card.");
        }
    }

    private Card strongestCard(List<Card> cards) {
        Card strongestCard = cards.get(0);
        for (Card c: cards){
            if (c.getAtk() > strongestCard.getAtk()) {
                strongestCard = c;
            }
        }
        return  strongestCard;
    }

    private Card weakestCard(List<Card> cards) {
        Card weakestCard = cards.get(0);
        for (Card c: cards){
            if (c.getHp() < weakestCard.getHp()) {
                weakestCard = c;
            }
        }
        return  weakestCard;
    }

    private Card cheapestCard(List<Card> cards) {
        Card cheapestCard = cards.get(0);
        for (Card c: cards){
            if (c.getPriceMana() < cheapestCard.getPriceMana()) {
                cheapestCard = c;
            }
        }
        return  cheapestCard;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
}