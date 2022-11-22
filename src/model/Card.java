package model;

import java.io.Serializable;

public abstract class Card implements  Serializable {
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
        this.hp = hp;
    }

    public void appliesDamage(int damage) {
        this.hp -= damage;
    }

    public void setTribeName(TypeOfTribe tribeName ) {
        this.tribeName = tribeName;
    }

    abstract void specialAttributeDescription();

    abstract short applySpecialAttack();

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void upgradeCard() {
        this.atk = (int) Math.round(this.atk*1.3);
        this.hp = (int) Math.round(this.hp*1.3);
        this.countUpgrade++;
    }

    public int getCountUpgrade() {
        return countUpgrade;
    }

    public void setCountUpgrade(int countUpgrade) {
        this.countUpgrade = countUpgrade;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", hp=" + hp +
                ", atk=" + atk +
                ", tribeName=" + tribeName;
    }


}
