package model;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private String name;
    private int hp;
    private int atk;
    private TypeOfTribe tribeName;

    public Card(String name, int hp, int atk, TypeOfTribe tribeName){
        this.name = name;
        this.atk = atk;
        this.hp = hp;
        this.tribeName = tribeName;
    }

    public int attack() {
        return this.atk;
    }

    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp += hp;
    }

    public void setTribeName(TypeOfTribe tribeName ) {
        this.tribeName = tribeName;
    }

    abstract void specialAttributeDescription();

    abstract short applySpecialAttack();
}
