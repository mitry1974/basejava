package model;

public interface Section {
    static Section createSection(SectionType t) {
        Section section;
        switch (t) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new StringArraySection();
                break;
            case EXPERIENCE:
            case EDUCATION:
                section = new OrganizationSection();
                break;
            default:
                section = new SimpleTextSection();
                break;
        }
        return section;
    }

    String toString();

    void clearData();
}
