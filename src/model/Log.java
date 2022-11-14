package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Log implements Serializable {
    private List<String> entries = new ArrayList<>();

    public void addEntry(String entry) {
        entries.add(entry);
    }

    public List<String> getEntries() {
        return entries;
    }
}
