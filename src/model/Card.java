package model;

import java.io.Serializable;
import java.util.List;

public abstract class Card extends Entity implements  Serializable {

    private TypeOfTribe tribeName;
    private int priceMana;

    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana, boolean canPlay){
        super(name,hp,atk,canPlay);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
    }

    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana){
        this(name,hp,atk,tribeName,priceMana, false);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
    }

    public TypeOfTribe getTribeName() {return this.tribeName;}

    public void specialAttributeDescription() {
        System.out.println("Special attack description: No special attack");
    }

    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
    }

    public int getPriceMana() {
        return priceMana;
    }

    public void upgradeThisCard(int level) {
        this.setAtk(this.getAtk() + level);
        this.setHp(this.getHp() + level);
    }
    @Override
    public String toString() {
        return this.getName() + " (" + this.getHp() + "HP, " + this.getAtk() + " ATK and cost " +this.getPriceMana() + " mana)";
    }


}
