package model;

public class SimpleTextSection extends AbstractSection {
    private String data;
    public SimpleTextSection(SectionType type) {
        super(type);
    }

    public void setData(String[] data) {
        this.data = data[0];
    }

    public String[] getData() {
        return new String[]{data};
    }

    @Override
    public void clearData() {
        data = "";
    }

    @Override
    public String toString() {
        return data;
    }
}
