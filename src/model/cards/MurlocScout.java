package model.cards;

import model.Card;
import model.Entity;
import model.Player;
import model.TypeOfTribe;

import java.io.Serializable;
import java.util.List;

public class MurlocScout extends Card implements Serializable {

    public MurlocScout() {
        super("Murloc Scout", 1,1,TypeOfTribe.MURLOCSCOUT,1);
    }

}
