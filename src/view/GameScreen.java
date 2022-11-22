package view;

import controller.PlayerActions;
import model.GameIO;
import model.GameModel;
import model.Log;
import model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen {

    private PlayerActions controller;
    private GameModel model;
    private LogPrinter logPrinter;

    public GameScreen(PlayerActions controller) {
        this.controller = controller;
        this.model = controller.getGameModel();
        this.logPrinter = new LogPrinter(model.getLog().getEntries());
    }

    private void printGameBoard() {
        model.printGameBoard();
    }

    public void start() {
        while(!model.endOfTheGame()) {
            List<String> possibleActions = new ArrayList<>(Arrays.asList("attack", "buy a card (3 mana)", "upgrade a card", "save and exit game", "exit game"));
            OptionsMenu<String> actionsMenu = new OptionsMenu<>("What do you want to do next", possibleActions);
            try {
                switch (actionsMenu.ask()) {
                    case "attack":
                        model.fight();
                        printGameBoard();
                        logPrinter.printLastEntries();
                        break;
                    case "buy a card (3 mana)":
                        model.takeACard(model.getPlayer());
                        logPrinter.printLastEntries();
                        break;
                    case "upgrade a card":
                        model.upgradeCard();
                        logPrinter.printLastEntries();
                        break;
                    case "save and exit game":
                        GameIO.saveGame(model);
                        System.out.println("Game saved!");
                    case "exit game":
                        System.out.println("Exiting game...");
                        return;
                    default:
                        break;
                }
            } catch(Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}
