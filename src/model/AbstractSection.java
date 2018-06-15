package model;

public abstract class AbstractSection implements Section {
    public static Section createSection(SectionType t) {
        Section section;
        switch (t) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new StringArraySection(t);
                break;
            default:
                section = new SimpleTextSection(t);
                break;
        }
        return section;
    }

    private SectionType type;

    protected AbstractSection(SectionType type) {
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }

    public String getTitle() {
        return type.getTitle();
    }
}
