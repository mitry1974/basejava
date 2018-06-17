package model;

import org.w3c.dom.Node;

public interface Section{
    static Section createSection(SectionType t) {
        Section section;
        switch (t) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new StringArraySection(t.getTitle());
                break;
            case EXPERIENCE:
            case EDUCATION:
                section = new OrganizationSection(t.getTitle());
                break;
            default:
                section = new SimpleTextSection(t.getTitle());
                break;
        }
        return section;
    }
    String toString();

    void clearData();

    void loadXml(Node rootNode);
}
