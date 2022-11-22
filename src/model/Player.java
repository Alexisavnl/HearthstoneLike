package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player implements Serializable {

    private static final int MAX_MANA = 10;
    private String name;
    private int hp;
    private List<Card> cards;
    private int gold;
    private int mana;

    private int defaultAtk;


    public Player(int hp,List<Card> cards,String name) {
        this.name = name;
        this.hp = hp;
        this.cards = cards;
    }

    public void setCards(List<Card> cards) {
         this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(short hp) {
        this.hp = hp;
    }

    public void applyDamages(int damages) {
        this.hp -= damages;
    }
    private Card getCard(Card card) {
        int indexOfCard = this.cards.indexOf(card);
        return cards.get(indexOfCard);
    }

    public void applyDamagesOnACard(int damages, Card card) {
        getCard(card).appliesDamage(damages);
        if(!card.isAlive()) {
            removeCard(card);
        }
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public boolean canBuyCard() {
        return mana > 3 && cards.size() < 4;
    }

    public int getDefaultAtk() {
        return defaultAtk;
    }

    public void setDefaultAtk(int defaultAtk) {
        this.defaultAtk = defaultAtk;
    }

    @Override
    public String toString() {
        String display = name + " have " + hp + " hp";
        display += "\n Card on the board:";
        if(cards.size() > 0) {
            for(Card card: cards){
                display += " - " + card.toString() + "\n";
            }
        } else {
            display += "No cards on the board";
        }
        return display;
    }
}
