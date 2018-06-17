package model;

public interface Section {
    static Section createSection(SectionType t) {
        Section section;
        switch (t) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new StringArraySection();
                break;
            default:
                section = new SimpleTextSection();
                break;
        }
        return section;
    }
    String toString();

    void setData(String[] data);

    String[] getData();

    void clearData();
}
