package view;

public enum DifficultyMode {

    Hard(2),
    Medium(1),
    Easy(0);

    private int choice;

    private DifficultyMode(int choice) {
        this.choice = choice;
    }

}
