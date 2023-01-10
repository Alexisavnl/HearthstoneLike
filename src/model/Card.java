package model;

import java.io.Serializable;

public abstract class Card extends Entity implements  Serializable {

    private TypeOfTribe tribeName;
    private int priceMana;

    private int countUpgrade;

    private boolean justPlaced;


    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana, boolean justPlaced){
        super(name,hp,atk,justPlaced);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
        this.countUpgrade = 0;
        this.justPlaced = justPlaced;
    }

    public Card(String name, int hp, int atk, TypeOfTribe tribeName, int priceMana){
        this(name,hp,atk,tribeName,priceMana, true);
        this.tribeName = tribeName;
        this.priceMana = priceMana;
        this.countUpgrade = 0;
    }
    public void setTribeName(TypeOfTribe tribeName ) {
        this.tribeName = tribeName;
    }

    public TypeOfTribe getTribeName() {return this.tribeName;}

    abstract void specialAttributeDescription();

    abstract short applySpecialAttack();


    public void upgradeCard() {
        this.setAtk((int) Math.round(this.getAtk() * 1.3));
        this.setHp((int) Math.round(this.getHp() * 1.3));
        this.countUpgrade++;
    }

    public int getCountUpgrade() {
        return countUpgrade;
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

    @Override
    public String toString() {
        return "name='" + this.getName() + '\'' +
                ", hp=" + this.getHp() +
                ", atk=" + this.getAtk() +
                ", tribeName=" + tribeName;
    }


}
