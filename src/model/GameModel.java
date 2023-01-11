package model;

import model.cards.*;
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
                add(new FireBall());
                add(new FireBall());
                add(new FireBall());
                add(new FireBall());
                add(new Blizzard());
                add(new Blizzard());
                add(new Blizzard());
                add(new ElvenArcher());
                add(new ElvenArcher());
                add(new ElvenArcher());
                add(new MurlocScout());
                add(new MurlocScout());
                add(new MurlocScout());
                add(new MurlocTidehunter());
                add(new MurlocTidehunter());
                add(new MurlocTidehunter());
                add(new RaidLeader());
                add(new RaidLeader());
                add(new RaidLeader());
            }
        };
        Collections.shuffle(pileOfCard);
        switch (difficultyMode) {
            case Easy:
                botPlayer = new Player(15, new ArrayList<>(), "The bot tower");
                break;
            case Medium:
                botPlayer = new Player(20, new ArrayList<>(), "The bot tower");
                break;
            case Hard:
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

    public void deadCardsCleaner() {
        Collection<Card> botCollection = botPlayer.getCardsInHand();
        List<Card> newBotCards = botCollection.stream().filter(i -> i.isAlive()
        ).collect(Collectors.toList());
        botPlayer.setCardsOnTheBoard(newBotCards);
        Collection<Card> playerCollection = botPlayer.getCardsInHand();
        List<Card> newPlayerCards = playerCollection.stream().filter(i -> i.isAlive()
        ).collect(Collectors.toList());
        thePlayer.setCardsOnTheBoard(newPlayerCards);
    }
    public void playerFight() {
        Entity entityPlayer;
        if(!thePlayer.isCanPlay() && thePlayer.getCardsOnTheBoard().size() == 0) {
            theLog.addEntry("No action is possible at this time. Place a card or skip your turn.");
            return;
        }
        Collection<Entity> allFighters = new ArrayList<>(thePlayer.getCardsOnTheBoard());
        allFighters.add(thePlayer);
        List<Entity> onlyFightersCanAttack = allFighters.stream().filter(i -> i.isCanPlay()
        ).collect(Collectors.toList());
        if(onlyFightersCanAttack.size() > 0) {

            OptionsMenu<Entity> cardAvailablePlayer = new OptionsMenu<>("\n\033[96mPick the card you want to use to attack:\n" +
                    "----------------------------------\033[0m", onlyFightersCanAttack);
            Entity entityChoosen = cardAvailablePlayer.ask();
            List<Entity> possibleActions = new ArrayList<Entity>(botPlayer.getCardsOnTheBoard());
            possibleActions.add(botPlayer);
            OptionsMenu<Entity> cardAvailableBot = new OptionsMenu<>("\n\033[96mPick the card to attack:\n" +
                    "----------------------------------\033[0m",  possibleActions);

            Entity e = cardAvailableBot.ask();
            e.appliesDamage(entityChoosen.getAtk());
            entityChoosen.appliesDamage(e.getAtk());
            deadCardsCleaner();
        } else {
            theLog.addEntry("No action is possible at this time. Place a card or skip your turn.");
            return;
        }

    }

    //proccessBot
    public void botFight() {
        Collection<Card> collection = botPlayer.getCardsInHand();
        //todo verifier si le filter macher bien
        List<Card> cardToPose = collection.stream().filter(i -> {
            if(i.getPriceMana() <= botPlayer.getMana()){
                theLog.addEntry("The bot has placed this card on the board " + i.toString());
                botPlayer.setMana(botPlayer.getMana() - i.getPriceMana());
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        botPlayer.getCardsInHand().removeAll(cardToPose);
        for(Card c: cardToPose){
            if(c.getHp() > 0){
                botPlayer.getCardsOnTheBoard().add(c);
            }
            c.applySpecialAttack(botPlayer, thePlayer);
        }
        List<Entity> botArmy = new ArrayList<Entity>(botPlayer.getCardsOnTheBoard());
        botArmy.add(botPlayer);
        Random r = new Random();

        if(botPlayer.getCardsOnTheBoard().size() > 0){
            for(Card card : List.copyOf(botPlayer.getCardsOnTheBoard())){
                if(card.isCanPlay()) {
                    if (thePlayer.getCardsOnTheBoard().size() == 0) {
                        theLog.addEntry("Player doesn't have cards on the board");
                        theLog.addEntry(card.getName() + " attack the player");
                        theLog.addEntry("Player HP : " + thePlayer.getHp());
                        theLog.addEntry("");
                        if (card.getTribeName().equals(TypeOfTribe.FIREBALL)) {
                            FireBall fireBall = (FireBall) card;
                            fireBall.fight(thePlayer);
                            botPlayer.getCardsOnTheBoard().remove(card);
                            theLog.addEntry("Fireball was used");
                        } else {
                            card.fight(thePlayer);
                        }
                    } else {
                        int indexTarget = r.nextInt(thePlayer.getCardsOnTheBoard().size());
                        Card targetCard = thePlayer.getCardsOnTheBoard().get(indexTarget);

                        theLog.addEntry("The bot attack this " + targetCard.toString());
                        theLog.addEntry("Your " + targetCard.getTribeName() + " before attack: " + targetCard.getHp() + "hp");


                        theLog.addEntry("Your " + targetCard.getName() + " after attack: " + targetCard.getHp() + "hp");
                        theLog.addEntry("Bot " + card.getName() + " before attack: " + card.getHp() + "hp");

                        deadCardsCleaner();

                    }
                }
            }
        } else {
            if(botPlayer.isCanPlay()){
                botPlayer.fight(thePlayer);
            }
        }
        isPlayerTurn = true;
    }



    public void newRound() {
        //todo export
        if(!thePlayer.isAlive()) {
            theLog.addEntry("The bot wins the game");
            return;
        }
        if(!botPlayer.isAlive()) {
            theLog.addEntry("You win the game");
            return;
        }
        this.round++;
        thePlayer.setMana(round);
        botPlayer.setMana(round);
        thePlayer.setGold(thePlayer.getGold() + 2);
        thePlayer.setCanPlay(true);
        botPlayer.setCanPlay(true);
        for(Card c: thePlayer.getCardsOnTheBoard()){
            c.setCanPlay(true);
        }
        for(Card c: botPlayer.getCardsOnTheBoard()){
            c.setCanPlay(true);
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
                Card drawnCard = pileOfCard.remove(0);
                if(player.getLevel() > 0) {
                    drawnCard.upgradeThisCard(player.getLevel());
                }
                player.addCardInHand(drawnCard);
                theLog.addEntry(player.getName() + " picked the card: " +drawnCard.toString());
        } else {
            theLog.addEntry("The pile is empty");
        }
    }

    public void putACardOnBoard() {
        if(thePlayer.getCardsInHand().size() > 0){
            if(cheapestCard(thePlayer.getCardsInHand()).getPriceMana() <= thePlayer.getMana() ) {
                OptionsMenu<Card> cardAvailable = new OptionsMenu<>("\033[96mPick the card you want to put on board: \n" +
                        "----------------------------------\033[0m", thePlayer.getCardsInHand());
                Card cardChosen = cardAvailable.ask();
                if (cardChosen.getPriceMana() > thePlayer.getMana()) {
                    theLog.addEntry("You don't have enough money to deposit this card");
                } else {
                    int indexOfCard = thePlayer.getCardsInHand().indexOf(cardChosen);
                    Card c = thePlayer.getCardsInHand().remove(indexOfCard);
                    if(c.getHp() != 0) {
                        c.setCanPlay(false);
                        thePlayer.addCardOnTheBoard(c);
                    }
                    thePlayer.setMana(thePlayer.getMana() - cardChosen.getPriceMana());
                    c.applySpecialAttack(thePlayer, botPlayer);
                    deadCardsCleaner();
                }
            } else {
                theLog.addEntry("You don't have enough money to deposit a card.");
            }
        } else {
            theLog.addEntry("You don't have cards in your hand.");
        }
    }

    public void takeCards(Player player, int numberOfCards) {
        for(int i = 0; i < numberOfCards; i++) {
            player.addCardInHand(pileOfCard.remove(0));
        }
    }

    public void verifyLifeCard(Card choosenCard,Card targetCard, Player attacker, Player attacked) {
        if (choosenCard.getAtk() >= targetCard.getHp()) {
            theLog.addEntry("Well played " + attacker.getName() + "! You kill the card.");
            if (choosenCard.getAtk() - targetCard.getHp() > 0) {
                attacked.appliesDamage(choosenCard.getAtk() - targetCard.getHp());
                theLog.addEntry(attacker.getName() + " managed to inflict direct damage to your opponent.");
            }
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

    public void upgradeAllCards() {
        if(thePlayer.getGold() >= 10 ){
            thePlayer.setLevel(thePlayer.getLevel() + 1);
            for(Card c: thePlayer.getCardsOnTheBoard()){
                c.upgradeThisCard(thePlayer.getLevel());
            }
            for(Card c: thePlayer.getCardsInHand()) {
                c.upgradeThisCard(thePlayer.getLevel());
            }
            thePlayer.setGold(thePlayer.getGold()-10);
            return;
        }
        theLog.addEntry("You don't have enough to upgrade.");

    }
}