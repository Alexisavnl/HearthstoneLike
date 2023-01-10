package model;

import java.util.List;

public interface Fight {

    public void fight(Card card, Player opponent);

    public void fight(Player opponent);

    public void fight(Entity entity);

}
