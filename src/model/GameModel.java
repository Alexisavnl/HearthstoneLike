package model;

import model.cards.*;
import org.w3c.dom.ls.LSOutput;
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
        System.out.println("borad debugger "+botPlayer.getCardsOnTheBoard());
        this.takeCards(thePlayer, 1);
        this.takeCards(botPlayer, 1);
        takeACard(thePlayer);
        System.out.println("board debugger "+botPlayer.getCardsOnTheBoard());
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
        Collection<Card> botCollection = botPlayer.getCardsOnTheBoard();
        List<Card> newBotCards = botCollection.stream().filter(i -> {
            if(!i.isAlive()){
                if(i.getTribeName()==TypeOfTribe.RAIDLEADER){
                    RaidLeader raidLeader = (RaidLeader) i;
                    raidLeader.removeEffect(botPlayer);
                }
                theLog.addEntry("This card enemy was dead "+i.getName());
            }
           return i.isAlive();
        }).collect(Collectors.toList());
        botPlayer.setCardsOnTheBoard(newBotCards);
        Collection<Card> playerCollection = thePlayer.getCardsOnTheBoard();
        List<Card> newPlayerCards = playerCollection.stream().filter(i -> {
            if(!i.isAlive()){
                if(i.getTribeName()==TypeOfTribe.RAIDLEADER){
                    RaidLeader raidLeader = (RaidLeader) i;
                    raidLeader.removeEffect(thePlayer);
                }
                theLog.addEntry("Your card was dead "+i.getName());
            }
            return i.isAlive();
            }
        ).collect(Collectors.toList());
        thePlayer.setCardsOnTheBoard(newPlayerCards);
    }
    public void playerFight() {
        System.out.println(thePlayer.isCanPlay());
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
            System.out.println("debugger " + botPlayer.getCardsOnTheBoard());
            List<Entity> possibleActions = new ArrayList<Entity>(botPlayer.getCardsOnTheBoard());
            possibleActions.add(botPlayer);
            System.out.println("debugger " + botPlayer.getCardsOnTheBoard());
            OptionsMenu<Entity> cardAvailableBot = new OptionsMenu<>("\n\033[96mPick the card to attack:\n" +
                    "----------------------------------\033[0m",  possibleActions);

            Entity e = cardAvailableBot.ask();
            theLog.addEntry("The bot: " + e.getName() + " before attack: " + e.getHp() + "hp");
            e.appliesDamage(entityChoosen.getAtk());
            theLog.addEntry("The bot: " + e.getName() + " after attack: " + e.getHp() + "hp\n");
            if(entityChoosen.getName() != "Your tower") {
                theLog.addEntry("Your " + entityChoosen.getName() + " before attack: " + entityChoosen.getHp() + "hp");
                entityChoosen.appliesDamage(e.getAtk());
                theLog.addEntry("Your " + entityChoosen.getName() + " after attack: " + entityChoosen.getHp() + "hp\n");
            }
            entityChoosen.setCanPlay(false);
            deadCardsCleaner();
        } else {
            theLog.addEntry("No action is possible at this time. Place a card or skip your turn.");
        }

    }

    public void botFight() {
        takeACard(botPlayer);
        Collection<Card> collection = botPlayer.getCardsInHand();
        //todo verifier si le filter macher bien
        List<Card> cardToPose = collection.stream().filter(i -> {
            if(i.getPriceMana() <= botPlayer.getMana()){
                System.out.println("The bot has placed this card on the board " + i.toString());
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
        System.out.println("debugger " + botArmy);
        Random r = new Random();

        for(Entity entity : List.copyOf(botArmy)){
            if(entity.isCanPlay()) {
                if (thePlayer.getCardsOnTheBoard().size() == 0) {
                    theLog.addEntry("You doesn't have cards on the board");
                    theLog.addEntry(entity.getName() + " attack your tower \n");
                    theLog.addEntry("Your tower before attack: " + thePlayer.getHp() + "hp");
                    thePlayer.appliesDamage(entity.getAtk());
                    theLog.addEntry("Your tower after attack: " + thePlayer.getHp() + "hp\n");
                } else {
                    System.out.println("debug carte sur le terrain "+thePlayer.getCardsOnTheBoard());
                    int indexTarget = r.nextInt(thePlayer.getCardsOnTheBoard().size());
                    Card targetCard = thePlayer.getCardsOnTheBoard().get(indexTarget);

                    theLog.addEntry("The bot use this " +(entity.getName() == "The bot tower" ? "tower" : entity.getName()) + " for attack your card " + targetCard.toString() + "\n");
                    theLog.addEntry("Your " + targetCard.getName() + " before attack: " + targetCard.getHp() + "hp");
                    targetCard.appliesDamage(entity.getAtk());
                    theLog.addEntry("Your " + targetCard.getName() + " after attack: " + targetCard.getHp() + "hp\n");
                    if(entity.getName() != "The bot tower") {
                        theLog.addEntry("Bot card " + entity.getName() + " before attack: " + entity.getHp() + "hp");
                        entity.appliesDamage(targetCard.getAtk());
                        theLog.addEntry("Bot " + entity.getName() + " after attack: " + entity.getHp() + "hp\n");
                    }
                    deadCardsCleaner();
                }
            }
        }

        isPlayerTurn = true;
        newRound();
    }



    public void newRound() {
        //todo export
        if(!thePlayer.isAlive()) {
            theLog.addEntry("The bot wins the game");
            theLog.addEntry("\033[31m __  __     ______     __  __        __         ______     ______     ______    \n" +
                    "/\\ \\_\\ \\   /\\  __ \\   /\\ \\/\\ \\      /\\ \\       /\\  __ \\   /\\  ___\\   /\\  ___\\   \n" +
                    "\\ \\____ \\  \\ \\ \\/\\ \\  \\ \\ \\_\\ \\     \\ \\ \\____  \\ \\ \\/\\ \\  \\ \\___  \\  \\ \\  __\\   \n" +
                    " \\/\\_____\\  \\ \\_____\\  \\ \\_____\\     \\ \\_____\\  \\ \\_____\\  \\/\\_____\\  \\ \\_____\\ \n" +
                    "  \\/_____/   \\/_____/   \\/_____/      \\/_____/   \\/_____/   \\/_____/   \\/_____/ \n" +
                    "                                                                                \033[0m");
            return;
        }
        if(!botPlayer.isAlive()) {
            theLog.addEntry("You win the game");
            theLog.addEntry("\033[32m __  __     ______     __  __        __     __     __     __   __    \n" +
                    "/\\ \\_\\ \\   /\\  __ \\   /\\ \\/\\ \\      /\\ \\  _ \\ \\   /\\ \\   /\\ \"-.\\ \\   \n" +
                    "\\ \\____ \\  \\ \\ \\/\\ \\  \\ \\ \\_\\ \\     \\ \\ \\/ \".\\ \\  \\ \\ \\  \\ \\ \\-.  \\  \n" +
                    " \\/\\_____\\  \\ \\_____\\  \\ \\_____\\     \\ \\__/\".~\\_\\  \\ \\_\\  \\ \\_\\\\\"\\_\\ \n" +
                    "  \\/_____/   \\/_____/   \\/_____/      \\/_/   \\/_/   \\/_/   \\/_/ \\/_/ \n" +
                    "                                                                     \033[0m");
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
    }



    public void playerHasFinishedRound() {
        isPlayerTurn = false;
    }


    public void printGameBoard() {
        theLog.addEntry(botPlayer.getStringHP());
        theLog.addEntry(thePlayer.getStringHP());
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
                List<Card> cardCanBuy = thePlayer.getCardsInHand().stream().filter(i->i.getPriceMana()<= thePlayer.getMana()).collect(Collectors.toList());
                OptionsMenu<Card> cardAvailable = new OptionsMenu<>("\n\033[96mPick the card you want to put on board: \n" +
                        "----------------------------------\033[0m", cardCanBuy);
                Card cardChosen = cardAvailable.ask();
                thePlayer.getCardsInHand().remove(cardChosen);
                if(cardChosen.getHp() > 0) {
                    cardChosen.setCanPlay(false);
                    thePlayer.addCardOnTheBoard(cardChosen);
                }
                thePlayer.setMana(thePlayer.getMana() - cardChosen.getPriceMana());
                cardChosen.applySpecialAttack(thePlayer, botPlayer);
                deadCardsCleaner();
            } else {
                theLog.addEntry("\033[31mWARNING:\033[0m You don't have enough money to deposit a card.");
            }
        } else {
            theLog.addEntry("\033[31mWARNING:\033[0m You don't have cards in your hand.");
        }
    }

    public void takeCards(Player player, int numberOfCards) {
        for(int i = 0; i < numberOfCards; i++) {
            player.addCardInHand(pileOfCard.remove(0));
        }
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