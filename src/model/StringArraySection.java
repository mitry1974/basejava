package model;

import java.util.ArrayList;
import java.util.List;

public class StringArraySection implements Section {
    private List<String> array = new ArrayList<>();

    protected StringArraySection() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
            sb.append('\n');
        }
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public void clearData() {
        array.clear();
    }

    public void setData(String[] data) {
        for (String s : data) {
            array.add(s);
        }
    }
}
