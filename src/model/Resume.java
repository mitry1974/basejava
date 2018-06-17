package model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;
    private static final String DEFAULT_NAME = "Unknown";

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
        sb.append(fullName + '\n');
        for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
            sb.append(e.getValue() + '\n');
        }

        sb.append(sections.get(SectionType.OBJECTIVE).toString());
        sb.append(sections.get(SectionType.PERSONAL).toString());
        sb.append(sections.get(SectionType.ACHIEVEMENT).toString());
        sb.append(sections.get(SectionType.QUALIFICATIONS).toString());
        sb.append(sections.get(SectionType.EXPERIENCE).toString());
        sb.append(sections.get(SectionType.EDUCATION).toString());
        return sb.toString();
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

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public String getSectionTitle(SectionType type) {
        return type.getTitle();
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void load(Node rootNode) {
        NodeList list = rootNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            SectionType sectionType = null;
            switch (node.getNodeName()) {
                case "title":
                    fullName = node.getTextContent();
                    break;
                case "contacts":
                    loadContacts(node);
                    break;
                case "personal":
                    sectionType = SectionType.PERSONAL;
                    break;
                case "achievement":
                    sectionType = SectionType.ACHIEVEMENT;
                    break;
                case "objective":
                    sectionType = SectionType.OBJECTIVE;
                    break;
                case "qualifications":
                    sectionType = SectionType.QUALIFICATIONS;
                    break;
                case "experience":
                    sectionType = SectionType.EXPERIENCE;
                    break;
                case "education":
                    sectionType = SectionType.EDUCATION;
                    break;
            }
            if (sectionType != null) {
                Section s = Section.createSection(sectionType);
                s.loadXml(node);
                sections.put(sectionType, s);

            }
        }
    }

    protected void loadContacts(Node node) {
        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            ContactType type = null;
            switch (n.getNodeName()) {
                case "skype":
                    type = ContactType.SKYPE;
                    break;
                case "email":
                    type = ContactType.EMAIL;
                    break;
                case "phone":
                    type = ContactType.PHONE;
                    break;
                case "link":
                    type = ContactType.LINK;
                    break;
            }

            if (type != null)
                contacts.put(type, n.getTextContent());
        }
    }
}