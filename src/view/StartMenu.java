package view;

import controller.PlayerActions;
import model.GameIO;
import model.GameModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartMenu {

    public void start() {
        List<String> possibleActions = new ArrayList<>(Arrays.asList("Start new game", "Load", "Exit game"));
        OptionsMenu<String> actionsMenu = new OptionsMenu<>("Welcome!", possibleActions);
        switch (actionsMenu.ask()) {
            case "Start new game":
                startNewGame();
                break;
            case "Load":
                try {
                    loadAndLaunchGame();
                } catch(Exception e) {
                    System.out.println("Failed to load game, try another option?");
                    start();
                }
                break;
            case "Exit game":
                System.out.println("Thanks for playing!");
        }
    }

    private void loadAndLaunchGame() throws ClassNotFoundException, IOException {
        new GameIO();
        GameModel game = GameIO.loadGame();
        launchGame(game);
    }

    private void launchGame(GameModel game) {
        PlayerActions mainController = new PlayerActions(game);
        GameScreen gameScreen = new GameScreen(mainController);
        gameScreen.start();
    }

    private void startNewGame() {
        OptionsMenu<DifficultyMode> difficultyMenu = new OptionsMenu<DifficultyMode>("Difficulty mode?", Arrays.asList(DifficultyMode.values()));
        DifficultyMode difficulty = difficultyMenu.ask();
        GameModel game = new GameModel(difficulty);
        launchGame(game);
    }
}
