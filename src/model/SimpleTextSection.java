package model;

public class SimpleTextSection implements Section {
    private String data;

    protected SimpleTextSection() {
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void clearData() {
        data = "";
    }

    @Override
    public String toString() {
        return data + "\n\n";
    }
}
