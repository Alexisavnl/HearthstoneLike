package view;

import java.util.List;
import java.util.Scanner;

/**
 * The OptionsMenu class is a generic class that creates a menu with a list of options for the user to choose from.
 * @param <OptionType> the type of the options in the menu
 */
public class OptionsMenu<OptionType> {
    private List<OptionType> options;

    private String question;
    private Scanner scanner;

    /**
     * Constructor that creates a new OptionsMenu with a given question and list of options.
     * @param question the question to be displayed to the user before the options.
     * @param options the list of options for the user to choose from.
     */
    public OptionsMenu(String question, List<OptionType> options) {
        this.options = options;
        this.question = question;
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu to the user and returns the selected option.
     * If the user inputs an invalid option, they will be prompted to try again.
     * @return the selected option of type OptionType.
     */
    public OptionType ask() {
        System.out.println(question);
        for(int i = 0; i < options.size(); i++) {
            System.out.println((i+1) + ") " + options.get(i));
        }
        System.out.println("Type an option number and press <enter>:");
        try {
            int optionIndex = Integer.parseInt(scanner.nextLine(), 10);
            return options.get(optionIndex-1);
        } catch(Exception e) {
            System.out.println("Invalid option, try again");
            return ask();
        }
    }
}
