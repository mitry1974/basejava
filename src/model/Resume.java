package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName.trim();
        this.uuid = uuid.trim();
    }

    public Resume() {
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName, sections, contacts);
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

        for (SectionType t : SectionType.values()) {
            if (sections.containsKey(t)) {
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
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    public void addContact(ContactType type, String data) {
        contacts.put(type, data);
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts.clear();
        this.contacts.putAll(contacts);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }
}