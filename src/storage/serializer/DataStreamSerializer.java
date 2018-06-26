package storage.serializer;

import model.*;
import util.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sType = SectionType.valueOf(dis.readUTF());
                Section section = null;
                switch (sType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        section = new TextSection();
                        readTextSection((TextSection) section, dis);
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        section = new ListSection();
                        readListSection((ListSection) section, dis);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        section = new OrganizationSection();
                        readOrganizationSection((OrganizationSection) section, dis);
                        break;
                }
                resume.addSection(sType, section);
            }

            return resume;
        }
    }

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> e : sections.entrySet()) {
                SectionType type = e.getKey();
                dos.writeUTF(type.toString());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection tSection = (TextSection) e.getValue();
                        writeTextSection(tSection, dos);
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ListSection lSection = (ListSection) e.getValue();
                        writeListSection(lSection, dos);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection oSection = (OrganizationSection) e.getValue();
                        writeOrganizationSection(oSection, dos);
                        break;
                }
            }
        }
    }

    private void writeListSection(ListSection ls, DataOutputStream dos) throws IOException {
        List<String> items = ls.getItems();
        dos.writeInt(items.size());
        for (String s : items) {
            dos.writeUTF(s);
        }
    }

    private void readListSection(ListSection ls, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }

        ls.setItems(list);
    }

    private void writeTextSection(TextSection ts, DataOutputStream dos) throws IOException {
        dos.writeUTF(ts.getContent());
    }

    private void readTextSection(TextSection ts, DataInputStream dis) throws IOException {
        ts.setContent(dis.readUTF());
    }


    private void writeOrganizationSection(OrganizationSection os, DataOutputStream dos) throws IOException {
        List<Organization> list = os.getOrganizations();
        dos.writeInt(list.size());
        for (Organization o : list) {
            Link link = o.getHomePage();
            dos.writeUTF(link.getName());
            if (link.getUrl() == null)
                dos.writeUTF("");
            else
                dos.writeUTF(link.getUrl());

            List<Organization.Position> positions = o.getPositions();
            dos.writeInt(positions.size());
            for (Organization.Position p : positions) {
                dos.writeUTF(DateUtil.printYearMonth(p.getStartDate()));
                dos.writeUTF(DateUtil.printYearMonth(p.getEndDate()));
                dos.writeUTF(p.getTitle());
                dos.writeUTF(p.getDescription());
            }
        }
    }

    private void readOrganizationSection(OrganizationSection os, DataInputStream dis) throws IOException {
        int oSize = dis.readInt();
        ArrayList<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < oSize; i++) {
            Organization o = new Organization();

            Link link = new Link();
            link.setName(dis.readUTF());
            String url = dis.readUTF();
            if (url.equals("")) {
                link.setUrl(null);
            } else {
                link.setUrl(url);
            }

            o.setHomePage(link);

            int pSize = dis.readInt();
            ArrayList<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < pSize; j++) {
                Organization.Position p = new Organization.Position();
                p.setStartDate(DateUtil.parseYearMonth(dis.readUTF()));
                p.setEndDate(DateUtil.parseYearMonth(dis.readUTF()));
                p.setTitle(dis.readUTF());
                p.setDescription(dis.readUTF());
                positions.add(p);
                o.setPositions(positions);
            }
            organizations.add(o);
        }
        os.setOrganizations(organizations);
    }
}
