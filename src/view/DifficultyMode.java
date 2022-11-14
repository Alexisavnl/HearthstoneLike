package view;

public enum DifficultyMode {

    hard(2),
    medium(1),
    easy(0);

    private int choice;

    private DifficultyMode(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }

}
