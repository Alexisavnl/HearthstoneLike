package model;

import java.io.Serializable;
import java.util.List;

public abstract class Card extends Entity implements  Serializable, Fight {

    private TypeOfTribe tribeName;
    private int priceMana;

    private boolean justPlaced;

    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana, boolean justPlaced){
        super(name,hp,atk,justPlaced);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
        this.justPlaced = justPlaced;
    }

    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana){
        this(name,hp,atk,tribeName,priceMana, true);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
    }

    public void setTribeName(TypeOfTribe tribeName ) {
        this.tribeName = tribeName;
    }

    public TypeOfTribe getTribeName() {return this.tribeName;}

    public void specialAttributeDescription() {
        System.out.println("Special attack description: No special attack");
    }

    public void applySpecialAttack(Player player, Player opponent) {
        this.specialAttributeDescription();
    }

    @Override
    public void fight(Card card, Player opponent) {
        card.appliesDamage(this.getAtk());
        this.appliesDamage(card.getAtk());
    }

    @Override
    public void fight(Player opponent) {
        opponent.appliesDamage(this.getAtk());
    }

    public int getPriceMana() {
        return priceMana;
    }

    public void setPriceMana(int priceMana) {
        this.priceMana = priceMana;
    }

    public boolean isJustPlaced() {
        return justPlaced;
    }

    public void setJustPlaced(boolean justPlaced) {
        this.justPlaced = justPlaced;
    }

    public void upgradeThisCard(int level) {
        this.setAtk(this.getAtk() + level);
        this.setHp(this.getHp() + level);
    }
    @Override
    public String toString() {
        return "name='" + this.getName() + '\'' +
                ", hp=" + this.getHp() +
                ", atk=" + this.getAtk() +
                ", tribeName=" + tribeName;
    }


}
