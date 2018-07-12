package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(type, new OrganizationSection(readList(dis, () -> new Organization(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Organization.Position(
                                        DateUtil.parseYearMonth(dis.readUTF()),
                                        DateUtil.parseYearMonth(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF()))))));
                        break;
                    default:
                        throw new IllegalStateException();
                }
            });
            return resume;
        }
    }

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeCollection(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, resume.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();

                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        writeCollection(dos, ((ListSection) entry.getValue()).getItems(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dos, ((OrganizationSection) entry.getValue()).getOrganizations(), o -> {
                            Link link = o.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(link.getUrl());

                            writeCollection(dos, o.getPositions(), p -> {
                                dos.writeUTF(DateUtil.printYearMonth(p.getStartDate()));
                                dos.writeUTF(DateUtil.printYearMonth(p.getEndDate()));
                                dos.writeUTF(p.getTitle());
                                dos.writeUTF(p.getDescription());

                            });
                        });
                        break;
                }
            });
        }
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    public interface ElementProcessor {
        void process() throws IOException;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());

        for (T item : collection) {
            writer.write(item);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        ArrayList<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }

        return list;
    }

}
