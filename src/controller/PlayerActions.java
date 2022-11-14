package controller;

import model.GameModel;

public class PlayerActions {

    private GameModel gameModel;

    public PlayerActions(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }


}
