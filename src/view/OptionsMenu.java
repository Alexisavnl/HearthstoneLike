package view;

import java.util.List;
import java.util.Scanner;

public class OptionsMenu<OptionType> {
    private List<OptionType> options;

    private String question;
    private Scanner scanner;

    public OptionsMenu(String question, List<OptionType> options) {
        this.options = options;
        this.question = question;
        scanner = new Scanner(System.in);
    }

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
