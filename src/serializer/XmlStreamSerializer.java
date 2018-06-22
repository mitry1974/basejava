package serializer;

import model.*;
import util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        this.xmlParser = new XmlParser(Resume.class, Link.class, Organization.class, OrganizationSection.class, TextSection.class, ListSection.class, Organization.Position.class);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader r = new InputStreamReader(is,StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(r);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, new OutputStreamWriter(os));
        }
    }
}