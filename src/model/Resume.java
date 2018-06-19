package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

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

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

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

        for(SectionType t : SectionType.values()){
            if(sections.containsKey(t)){
                Section s = sections.get(t);
                Objects.requireNonNull(s);
                sb.append('\n');
                sb.append(t.getTitle());
                sb.append('\n');
                sb.append(s.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public void addContact(ContactType type, String data) {
        contacts.put(type, data);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }
}