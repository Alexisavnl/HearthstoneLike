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
        List<String> possibleActions = new ArrayList<>(Arrays.asList("Start new game", "Load", "Rules before starting game", "Exit game"));
        OptionsMenu<String> actionsMenu = new OptionsMenu<>("\033[96mWelcome to our HearthStone like!\n----------------------------------\033[0m", possibleActions);
        System.out.println("\033[33m                                                                                                                        \n" +
                "        ,--,                                                                                                            \n" +
                "      ,--.'|                                   ___      ,---,      .--.--.       ___                                    \n" +
                "   ,--,  | :                                 ,--.'|_  ,--.' |     /  /    '.   ,--.'|_                                  \n" +
                ",---.'|  : '                        __  ,-.  |  | :,' |  |  :    |  :  /`. /   |  | :,'   ,---.        ,---,            \n" +
                "|   | : _' |                      ,' ,'/ /|  :  : ' : :  :  :    ;  |  |--`    :  : ' :  '   ,'\\   ,-+-. /  |           \n" +
                ":   : |.'  |   ,---.     ,--.--.  '  | |' |.;__,'  /  :  |  |,--.|  :  ;_    .;__,'  /  /   /   | ,--.'|'   |   ,---.   \n" +
                "|   ' '  ; :  /     \\   /       \\ |  |   ,'|  |   |   |  :  '   | \\  \\    `. |  |   |  .   ; ,. :|   |  ,\"' |  /     \\  \n" +
                "'   |  .'. | /    /  | .--.  .-. |'  :  /  :__,'| :   |  |   /' :  `----.   \\:__,'| :  '   | |: :|   | /  | | /    /  | \n" +
                "|   | :  | '.    ' / |  \\__\\/: . .|  | '     '  : |__ '  :  | | |  __ \\  \\  |  '  : |__'   | .; :|   | |  | |.    ' / | \n" +
                "'   : |  : ;'   ;   /|  ,\" .--.; |;  : |     |  | '.'||  |  ' | : /  /`--'  /  |  | '.'|   :    ||   | |  |/ '   ;   /| \n" +
                "|   | '  ,/ '   |  / | /  /  ,.  ||  , ;     ;  :    ;|  :  :_:,''--'.     /   ;  :    ;\\   \\  / |   | |--'  '   |  / | \n" +
                ";   : ;--'  |   :    |;  :   .'   \\---'      |  ,   / |  | ,'      `--'---'    |  ,   /  `----'  |   |/      |   :    | \n" +
                "|   ,/       \\   \\  / |  ,     .-./           ---`-'  `--''                     ---`-'           '---'        \\   \\  /  \n" +
                "'---'         `----'   `--`---'                                                                                `----'   \n" +
                "                                                                                                                        \033[0m");
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
            case "Rules before starting game":
                System.out.println("\n\033[31mRules:\033[0m");
                System.out.println("\033[31m1.\033[0m Each player starts with a deck of 3 cards that are used to summon creatures, cast spells, and deal damage to the opponent.");
                System.out.println("\033[31m2.\033[0m The game begins with 1 mana for each player. The number of mana crystals increases by 1 each turn up to a maximum of 10.");
                System.out.println("\033[31m3.\033[0m Each card has a mana cost that is used to summon. Players can only use the available mana crystals to play cards.");
                System.out.println("\033[31m4.\033[0m Creatures can be used to attack the opponent or other creatures. Creatures have health points and take damage when attacked.");
                System.out.println("\033[31m5.\033[0m The player who deals damage to the opponent until their health points reach 0 wins the game.\n");
                startNewGame();
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
        OptionsMenu<DifficultyMode> difficultyMenu = new OptionsMenu<DifficultyMode>("\n\033[96mChoose a difficulty mode :\n----------------------------------\033[0m", Arrays.asList(DifficultyMode.values()));
        DifficultyMode difficulty = difficultyMenu.ask();
        GameModel game = new GameModel(difficulty);
        launchGame(game);
    }
}
