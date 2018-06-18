package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    {
        for (SectionType t : SectionType.values()) {
            sections.put(t, Section.createSection(t));
        }
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName);
        sb.append('\n');
        for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
            sb.append(e.getValue());
            sb.append('\n');
        }

        sb.append(SectionType.OBJECTIVE.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.OBJECTIVE).toString());

        sb.append(SectionType.PERSONAL.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.PERSONAL).toString());

        sb.append(SectionType.ACHIEVEMENT.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.ACHIEVEMENT).toString());

        sb.append(SectionType.QUALIFICATIONS.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.QUALIFICATIONS).toString());

        sb.append(SectionType.EXPERIENCE.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.EXPERIENCE).toString());

        sb.append(SectionType.EDUCATION.getTitle());
        sb.append('\n');
        sb.append(sections.get(SectionType.EDUCATION).toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public void addContact(ContactType type, String data) {
        contacts.put(type, data);
    }

    public void setSectionData(SectionType type, Section section) {
        sections.put(type, section);
    }
}