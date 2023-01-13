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
                List<String> possibleActions = new ArrayList<>(Arrays.asList("Attack", "Put a card on the board", "End of round", "Upgrade all cards", "\033[32mSave and exit game\033[0m", "\033[31mExit game\033[0m"));
                OptionsMenu<String> actionsMenu = new OptionsMenu<>("\n\033[96mMake a choice:\033[0m\n" +
                        "\033[96m----------------------------------\033[0m\n" +
                        "\033[32mPlayer HP: " + model.getPlayer().getHp() + "\033[0m\n" +
                        "\033[31mBot HP: " + model.getBotPlayer().getHp() + "\033[0m\n"
                        , possibleActions);
                logPrinter.printLastEntries();
                try {
                    switch (actionsMenu.ask()) {
                        case "Attack":
                            model.playerFight();
                            printGameBoard();
                            logPrinter.printLastEntries();
                            break;
                        case "Put a card on the board":
                            model.putACardOnBoard();
                            logPrinter.printLastEntries();
                            break;
                        case "End of round":
                            model.playerHasFinishedRound();
                            logPrinter.printLastEntries();
                            break;
                        case "Upgrade all cards":
                            model.upgradeAllCards();
                            logPrinter.printLastEntries();
                            break;
                        case "\033[32mSave and exit game\033[0m":
                            GameIO.saveGame(model);
                            System.out.println("Game saved!");
                        case "\033[31mExit game\033[0m":
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
