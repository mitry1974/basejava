package model;

import java.util.ArrayList;
import java.util.Collections;
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
        return sb.toString();
    }

    @Override
    public void setData(String[] data) {
        clearData();
        Collections.addAll(array, data);
    }

    @Override
    public String[] getData() {
        return array.toArray(new String[0]);
    }

    @Override
    public void clearData() {
        array.clear();
    }
}
