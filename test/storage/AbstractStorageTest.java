package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Before;
import org.junit.Test;
import util.Config;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    private static final String UUID4 = UUID.randomUUID().toString();

    private static final Resume R1 = new Resume(UUID1, "Девелопер 1");
    private static final Resume R2 = new Resume(UUID2, "Девелопер 2");
    private static final Resume R3 = new Resume(UUID3, "Девелопер 3");
    private static final Resume R4 = new Resume(UUID4, "Девелопер 4");


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        fillResume1();
        fillResume2();
        fillResume3();
        fillResume4();

        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID2, "New Name");
        newResume.addContact(ContactType.EMAIL, "newmail@google.com");
        newResume.addContact(ContactType.SKYPE, "newSkype");
        newResume.addContact(ContactType.LINK, "new link.ru");

        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateUnExisting() {
        storage.update(R4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        List<Resume> sortedList = Arrays.asList(R1, R2, R3);
        Collections.sort(sortedList);
        assertEquals(sortedList, list);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertEquals(R4, storage.get(R4.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExisting() {
        storage.save(R1);
    }

    private void fillResume1() {
        R1.addContact(ContactType.PHONE, "+72326675345");
        R1.addContact(ContactType.EMAIL, "test@test.ru");
        R1.addContact(ContactType.SKYPE, "skype.contact");


        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        R1.addSection(SectionType.PERSONAL, personalSection);

        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        R1.addSection(SectionType.OBJECTIVE, objectiveSection);
        ListSection achievmentSection = new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        R1.addSection(SectionType.ACHIEVEMENT, achievmentSection);

        ListSection qualificationsSection = new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate"));
        R1.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Organization.Position op1 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Organization o1 = new Organization("Java Online Projects", "http://www.javaops.ru", op1);
        Organization.Position op2 = new Organization.Position(YearMonth.parse("10/2014", formatter), YearMonth.parse("01/2016", formatter), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization o2 = new Organization("Wrike", "www.wrike.com", op2);
        Organization.Position op3 = new Organization.Position(YearMonth.parse("04/2012", formatter), YearMonth.parse("10/2014", formatter), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Organization o3 = new Organization("RIT Center", null, op3);
        Organization.Position op4 = new Organization.Position(YearMonth.parse("12/2010", formatter), YearMonth.parse("04/2012", formatter), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        Organization o4 = new Organization("Luxoft (Deutsche Bank)", "www.luxsoft.com", op4);
        OrganizationSection expSection = new OrganizationSection(Arrays.asList(o1, o2, o3, o4));
        R1.addSection(SectionType.EXPERIENCE, expSection);


        Organization.Position op5 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        Organization o5 = new Organization("Coursera", null, op5);

        Organization.Position op6 = new Organization.Position(YearMonth.parse("03/2011", formatter), YearMonth.parse("04/2011", formatter), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", "");
        Organization o6 = new Organization("Luxoft", "www.luxsoft.com", op6);

        Organization.Position op7 = new Organization.Position(YearMonth.parse("01/2005", formatter), YearMonth.parse("04/2005", formatter), "3 месяца обучения мобильным IN сетям (Берлин)", "");
        Organization o7 = new Organization("Siemens AG", null, op7);

        Organization.Position op8 = new Organization.Position(YearMonth.parse("09/1993", formatter), YearMonth.parse("07/1996", formatter), "Аспирантура (программист С, С++)", "");
        Organization.Position op9 = new Organization.Position(YearMonth.parse("09/1987", formatter), YearMonth.parse("07/1993", formatter), "Инженер (программист Fortran, C)", "");
        Organization o8 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null, op8, op9);

        OrganizationSection eduSection = new OrganizationSection(Arrays.asList(o5, o6, o7, o8));
        R1.addSection(SectionType.EDUCATION, eduSection);
*/

    }

    @Test
    public void delete() {
        storage.delete(UUID1);
        assertEquals(2, storage.size());
    }

    private void fillResume2() {
        R2.addContact(ContactType.PHONE, "+2222222");
        R2.addContact(ContactType.EMAIL, "test2@test.ru");
        R2.addContact(ContactType.SKYPE, "skype2.contact");


        TextSection personalSection = new TextSection("Очень умный и креативный Девелопер 2");
        R2.addSection(SectionType.PERSONAL, personalSection);

        TextSection objectiveSection = new TextSection("Objective Двелопера 2");
        R2.addSection(SectionType.OBJECTIVE, objectiveSection);

        ListSection achievmentSection = new ListSection(Arrays.asList("Умение Девелопера 2 номер 1", "Умение Девелопера 2 номер 2", "Умение Девелопера 3 номер 3", "Умение девелопера 2 номер 4"));
        R2.addSection(SectionType.ACHIEVEMENT, achievmentSection);

        ListSection qualificationsSection = new ListSection(Arrays.asList("Квалификация девелопера 2 номер 1", "Квалификация девелопера 2 номер 2", "Квалификация девелопера 2 номер 3", "Квалификация девелопера 2 номер 4"));
        R2.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Organization.Position op1 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 2, позиция 1", "Девелопер 2, Описание 1");
        Organization o1 = new Organization("Девелопер 2, организация 1", "http://www.dev2org1.ru", op1);
        Organization.Position op2 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 2, позиция 2", "Девелопер 2, Описание 2");
        Organization o2 = new Organization("Девелопер 2, организация 2", "http://www.dev2org1.ru", op1);
        Organization.Position op3 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 2, позиция 3", "Девелопер 2, Описание 3");
        Organization o3 = new Organization("Девелопер 2, организация 3", "http://www.dev2org1.ru", op1);
        Organization.Position op4 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 2, позиция 4", "Девелопер 2, Описание 4");
        Organization o4 = new Organization("Девелопер 2, организация 4", "http://www.dev2org1.ru", op1);
        OrganizationSection expSection = new OrganizationSection(Arrays.asList(o1, o2, o3, o4));
        R2.addSection(SectionType.EXPERIENCE, expSection);


        Organization.Position op5 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 2, курс 1", "");
        Organization o5 = new Organization("Девелопер 2, обучение 1", null, op5);

        Organization.Position op6 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 2, курс 2", "");
        Organization o6 = new Organization("Девелопер 2, обучение 2", null, op5);
        Organization.Position op7 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 2, курс 3", "");
        Organization o7 = new Organization("Девелопер 2, обучение 3", null, op5);

        Organization.Position op8 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 2, курс 4", "");
        Organization.Position op9 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 2, курс 5", "");
        Organization o8 = new Organization("Девелопер 2, обучение 4", null, op8, op9);

        OrganizationSection eduSection = new OrganizationSection(Arrays.asList(o5, o6, o7, o8));
        R2.addSection(SectionType.EDUCATION, eduSection);
*/
    }

    private void fillResume3() {
        R3.addContact(ContactType.PHONE, "+3333333");
        R3.addContact(ContactType.EMAIL, "test3@test.ru");
        R3.addContact(ContactType.SKYPE, "skype3.contact");


        TextSection personalSection = new TextSection("Очень умный и креативный Девелопер 3");
        R3.addSection(SectionType.PERSONAL, personalSection);

        TextSection objectiveSection = new TextSection("Objective Девелопера 3");
        R3.addSection(SectionType.OBJECTIVE, objectiveSection);

        ListSection achievmentSection = new ListSection(Arrays.asList("Умение девелопера 3 номер 1", "Умение девелопера 3 номер 2", "Умение Девелопера 3 номер 3", "Умение девелопера 3 номер 4"));
        R3.addSection(SectionType.ACHIEVEMENT, achievmentSection);

        ListSection qualificationsSection = new ListSection(Arrays.asList("Квалификация девелопера 3 номер 1", "Квалификация девелопера 3 номер 2", "Квалификация девелопера 3 номер 3", "Квалификация девелопера 3 номер 4"));
        R3.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Organization.Position op1 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 3, позиция 1", "Девелопер 3, Описание 1");
        Organization o1 = new Organization("Девелопер 3, организация 1", "http://www.dev2org1.ru", op1);
        Organization.Position op2 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 3, позиция 2", "Девелопер 3, Описание 2");
        Organization o2 = new Organization("Девелопер 3, организация 2", "http://www.dev2org1.ru", op1);
        Organization.Position op3 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 3, позиция 3", "Девелопер 3, Описание 3");
        Organization o3 = new Organization("Девелопер 3, организация 3", "http://www.dev2org1.ru", op1);
        Organization.Position op4 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 3, позиция 4", "Девелопер 3, Описание 4");
        Organization o4 = new Organization("Девелопер 3, организация 4", "http://www.dev2org1.ru", op1);
        OrganizationSection expSection = new OrganizationSection(Arrays.asList(o1, o2, o3, o4));
        R3.addSection(SectionType.EXPERIENCE, expSection);


        Organization.Position op5 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 3, курс 1", "");
        Organization o5 = new Organization("Девелопер 3, обучение 1", null, op5);

        Organization.Position op6 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 3, курс 2", "");
        Organization o6 = new Organization("Девелопер 3, обучение 2", null, op5);
        Organization.Position op7 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 3, курс 3", "");
        Organization o7 = new Organization("Девелопер 3, обучение 3", null, op5);

        Organization.Position op8 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 3, курс 4", "");
        Organization.Position op9 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 3, курс 5", "");
        Organization o8 = new Organization("Девелопер 3, обучение 4", null, op8, op9);

        OrganizationSection eduSection = new OrganizationSection(Arrays.asList(o5, o6, o7, o8));
        R3.addSection(SectionType.EDUCATION, eduSection);
*/
    }

    private void fillResume4() {
        R4.addContact(ContactType.PHONE, "+4444444");
        R4.addContact(ContactType.EMAIL, "test4@test.ru");
        R4.addContact(ContactType.SKYPE, "skype4.contact");


        TextSection personalSection = new TextSection("Очень умный и креативный Девелопер 4");
        R4.addSection(SectionType.PERSONAL, personalSection);

        TextSection objectiveSection = new TextSection("Objective Девелопера 4");
        R4.addSection(SectionType.OBJECTIVE, objectiveSection);

        ListSection achievmentSection = new ListSection(Arrays.asList("Умение Девелопера 4 номер 1", "Умение Девелопера 4 номер 2", "Умение Девелопера 3 номер 3", "Умение девелопера 4 номер 4"));
        R4.addSection(SectionType.ACHIEVEMENT, achievmentSection);

        ListSection qualificationsSection = new ListSection(Arrays.asList("Квалификация девелопера 4 номер 1", "Квалификация девелопера 4 номер 2", "Квалификация девелопера 4 номер 3", "Квалификация девелопера 4 номер 4"));
        R4.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Organization.Position op1 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 4, позиция 1", "Девелопер 4, Описание 1");
        Organization o1 = new Organization("Девелопер 4, организация 1", "http://www.dev2org1.ru", op1);
        Organization.Position op2 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 4, позиция 2", "Девелопер 4, Описание 2");
        Organization o2 = new Organization("Девелопер 4, организация 2", "http://www.dev2org1.ru", op1);
        Organization.Position op3 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 4, позиция 3", "Девелопер 4, Описание 3");
        Organization o3 = new Organization("Девелопер 4, организация 3", "http://www.dev2org1.ru", op1);
        Organization.Position op4 = new Organization.Position(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Девелопер 4, позиция 4", "Девелопер 4, Описание 4");
        Organization o4 = new Organization("Девелопер 4, организация 4", "http://www.dev2org1.ru", op1);
        OrganizationSection expSection = new OrganizationSection(Arrays.asList(o1, o2, o3, o4));
        R4.addSection(SectionType.EXPERIENCE, expSection);


        Organization.Position op5 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 4, курс 1", "");
        Organization o5 = new Organization("Девелопер 4, обучение 1", null, op5);

        Organization.Position op6 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 4, курс 2", "");
        Organization o6 = new Organization("Девелопер 4, обучение 2", null, op5);
        Organization.Position op7 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 4, курс 3", "");
        Organization o7 = new Organization("Девелопер 4, обучение 3", null, op5);

        Organization.Position op8 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 4, курс 4", "");
        Organization.Position op9 = new Organization.Position(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "Девелопер 4, курс 5", "");
        Organization o8 = new Organization("Девелопер 4, обучение 4", null, op8, op9);

        OrganizationSection eduSection = new OrganizationSection(Arrays.asList(o5, o6, o7, o8));
        R4.addSection(SectionType.EDUCATION, eduSection);
*/

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete(UUID4);
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getUnExisting() {
        storage.get(UUID4);
    }

}
