package storage;

import model.ContactType;
import model.Resume;
import model.SectionType;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.List;

public class ResumeTest {

   private static final String UUID1 = "uuid1";
   private static final Resume resume1 = new Resume(UUID1, "");



    public ResumeTest() {
    }

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse("TestResume.xml");
        resume1.load(document.getDocumentElement());
    }

    @Test
    public void printResume() {
        System.out.println(resume1.toString());
    }

}
