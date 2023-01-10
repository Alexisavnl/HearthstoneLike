package model;

import java.io.Serializable;
import java.util.List;

public abstract class Entity implements Serializable {
    private String name;
    private int hp;
    private int atk;

    private boolean isPlayed;
    public Entity(String name, int hp, int atk, boolean isPlayed) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.isPlayed = isPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void appliesDamage(int damage) {
        this.hp -= damage;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }
}
