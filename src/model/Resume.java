package model;

import sun.swing.SwingUtilities2;

import java.lang.reflect.Array;
import java.util.*;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private static final String DEFAULT_NAME = "Unknown";

    private final Map<SectionType, Section> sections = new HashMap<>();
    private final Map<String, Contact> contacts = new HashMap<>();

    {
        for(SectionType t:SectionType.values()){
            sections.put(t, AbstractSection.createSection(t));
        }
    }

    public Resume() {
        this(UUID.randomUUID().toString(), DEFAULT_NAME);
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

    public List<Contact> getContacts() {
        return Arrays.asList(contacts.values().toArray(new Contact[0]));
    }

    public void addContact(String contactUuid, ContactType type, String data) {
        Contact c = new Contact(contactUuid, type, data);
        contacts.put(contactUuid, c);
    }

    public String[] getSectionData(SectionType type) {
        return getSection(type).getData();
    }

    public String getSectionTitle(SectionType type) {
        return getSection(type).getTitle();
    }

    public void setSectionData(SectionType type, String[] data) {
        getSection(type).setData(data);
    }

    protected Section getSection(SectionType type){
        return sections.get(type);
    }
}