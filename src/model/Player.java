package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Entity implements Serializable {

    private static final int MAX_MANA = 10;

    private List<Card> cardsInHand;

    private List<Card> cardsOnTheBoard;

    private int gold;
    private int mana;
    private int level;


    public Player(int hp,List<Card> cards,String name) {
        super(name, hp, 1, true);
        this.cardsInHand = cards;
        this.cardsOnTheBoard = new ArrayList<>();
        this.gold = 8;
        this.mana = 1;
        this.level = 0;
    }

    public void setCards(List<Card> cards) {
         this.cardsInHand = cards;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardInHand(Card cardsInHand) {
        this.cardsInHand.add(cardsInHand);
    }
    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public List<Card> getCardsOnTheBoard() {
        return cardsOnTheBoard;
    }

    public void setCardsOnTheBoard(List<Card> cardsOnTheBoard) {
        this.cardsOnTheBoard = cardsOnTheBoard;
    }

    public void addCardOnTheBoard(Card cardsOnTheBoard) {
        this.cardsOnTheBoard.add(cardsOnTheBoard);
    }

    public void setGold(int gold) {this.gold = gold;}

    public int getGold() {return gold;}

    public void setMana(int mana) {
        if(mana < MAX_MANA){
            this.mana = mana;
        }
    }

    public int getMana() {return mana;}

    private Card getCard(Card card) {
        int indexOfCard = this.cardsInHand.indexOf(card);
        return cardsInHand.get(indexOfCard);
    }

    public void applyDamagesOnACard(int damages, Card card) {
        getCard(card).appliesDamage(damages);
        if(!card.isAlive()) {
            removeCard(card);
        }
    }

    public void removeCard(Card card) {
        cardsInHand.remove(card);
    }

    public void setLevel(int level) {this.level = level;}

    public int getLevel() {return level;}

    public void fight(Player opponent) {
        int opponentHPBeforeAttack = opponent.getHp();
        opponent.appliesDamage(this.getAtk());
        System.out.println(opponent.getName() + " has lost " + this.getAtk() + " : " + opponentHPBeforeAttack + " HP -> " + opponent.getHp() + " HP.");
    }

    public String getStringHP(){
        return this.getName() + " have " + this.getHp() + " HP";
    }

    @Override
    public String toString() {
        return this.getName() + "("+ this.getAtk() + " ATK, " + this.getHp() +" HP)";
    }


}
