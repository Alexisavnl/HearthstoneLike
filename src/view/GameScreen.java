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
            if(model.isPlayerTurn()) {
                List<String> possibleActions = new ArrayList<>(Arrays.asList("attack", "put a card on the board", "end of round", "upgrade all cards", "\033[32msave and exit game\033[0m", "\033[31mexit game\033[0m"));
                OptionsMenu<String> actionsMenu = new OptionsMenu<>("What do you want to do next", possibleActions);
                try {
                    switch (actionsMenu.ask()) {
                        case "attack":
                            model.playerFight();
                            printGameBoard();
                            logPrinter.printLastEntries();
                            break;
                        case "put a card on the board":
                            model.putACardOnBoard();
                            logPrinter.printLastEntries();
                            break;
                        case "end of round":
                            model.playerHasFinishedRound();
                            logPrinter.printLastEntries();
                            break;
                        case "upgrade all cards":
                            model.upgradeAllCards();
                            logPrinter.printLastEntries();
                            break;
                        case "\033[32msave and exit game\033[0m":
                            GameIO.saveGame(model);
                            System.out.println("Game saved!");
                        case "\033[31mexit game\033[0m":
                            System.out.println("Exiting game...");
                            return;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }

            } else {
                model.botFight();
                logPrinter.printLastEntries();
            }
        }
    }
}
