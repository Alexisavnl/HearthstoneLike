package view;

import java.util.List;


public class LogPrinter {
    private int printedEntriesCount = 0;
    private List<String> entriesList;

    public LogPrinter(List<String> entriesList) {
        this.entriesList = entriesList;
    }

    public void printLastEntries() {
        System.out.println(""); // empty line
        for(;printedEntriesCount < entriesList.size();printedEntriesCount++) {
            System.out.println(entriesList.get((printedEntriesCount)));
        }
        System.out.println(""); // empty line
    }
}
