package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private int hp;
    private List<Card> cards = new ArrayList<>();


    public Player(int hp,List<Card> cards) {
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

    public boolean isAlive() {
        return this.hp > 0;
    }
}
