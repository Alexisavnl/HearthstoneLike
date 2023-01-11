package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.GameModel;
import view.DifficultyMode;

public class GameIO {

    public static final String SAVED_GAME_LOCATION = "./save.data";


    public static GameModel loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(new File(SAVED_GAME_LOCATION));
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        GameModel game = (GameModel) objectIn.readObject();
        fileIn.close();
        objectIn.close();
        return game;
    }


    public static void main(String[] args) {
        GameModel game = new GameModel(DifficultyMode.Medium);
        try {
            GameIO.saveGame(game);
            GameModel loadedGame = GameIO.loadGame();
            System.out.println("Game saved and loaded succesfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(GameModel game) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(SAVED_GAME_LOCATION);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(game);
        objectOut.close();
        fileOut.close();
    }
}
