package model;

import java.lang.annotation.ElementType;
import java.util.*;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private static final String DEFAULT_NAME = "Unknown";

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts  = new EnumMap<>(ContactType.class);

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
        return uuid + " - " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType type, String data) {
        contacts.put(type, data);
    }

    public String getContact(ContactType type){
        return contacts.get(type);
    }

    public String[] getSectionData(SectionType type) {
        return getSection(type).getData();
    }

    public String getSectionTitle(SectionType type) {
        return type.getTitle();
    }

    public void setSectionData(SectionType type, String[] data) {
        getSection(type).setData(data);
    }

    protected Section getSection(SectionType type) {
        return sections.get(type);
    }
}