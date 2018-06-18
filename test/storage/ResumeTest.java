package storage;

import model.*;
import org.junit.Before;
import org.junit.Test;

public class ResumeTest {

    private final Storage storage = new ListStorage();

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID1, "Жугдэрдэмидийн Гуррагча");


    public ResumeTest() {
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);

        resume1.addContact(ContactType.PHONE, "+72326675345");
        resume1.addContact(ContactType.EMAIL, "test@test.ru");
        resume1.addContact(ContactType.SKYPE, "skype.contact");

        SimpleTextSection personalSection = (SimpleTextSection) Section.createSection(SectionType.PERSONAL);
        personalSection.setData("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume1.setSectionData(SectionType.PERSONAL, personalSection);

        SimpleTextSection objectiveSection = (SimpleTextSection) Section.createSection(SectionType.OBJECTIVE);
        objectiveSection.setData("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume1.setSectionData(SectionType.OBJECTIVE, objectiveSection);

        StringArraySection achievmentSection = (StringArraySection) Section.createSection(SectionType.ACHIEVEMENT);
        achievmentSection.setData(new String[]{
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        });
        resume1.setSectionData(SectionType.ACHIEVEMENT, achievmentSection);

        StringArraySection qualificationsSection = (StringArraySection) Section.createSection(SectionType.QUALIFICATIONS);
        qualificationsSection.setData(new String[]{
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
                "Родной русский, английский \"upper intermediate"});
        resume1.setSectionData(SectionType.QUALIFICATIONS, qualificationsSection);

        OrganizationSection expSection = (OrganizationSection) Section.createSection(SectionType.EXPERIENCE);

        Organization o1 = new Organization("Java Online Projects");
        o1.addPosition("10/2013", "now", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        expSection.addOrganization(o1);
        Organization o2 = new Organization("Wrike");
        o2.addPosition("10/2014", "01/2016", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        expSection.addOrganization(o2);
        Organization o3 = new Organization("RIT Center");
        o3.addPosition("04/2012", "10/2014", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        expSection.addOrganization(o3);
        Organization o4 = new Organization("Luxoft (Deutsche Bank)");
        o4.addPosition("12/2010", "04/2012", "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        expSection.addOrganization(o4);
        resume1.setSectionData(SectionType.EXPERIENCE, expSection);


        OrganizationSection eduSection = (OrganizationSection) Section.createSection(SectionType.EDUCATION);
        Organization o5 = new Organization("Coursera");
        o5.addPosition("03/2013", "05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        eduSection.addOrganization(o5);

        Organization o6 = new Organization("Luxoft");
        o6.addPosition("03/2011", "04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", "");
        eduSection.addOrganization(o6);

        Organization o7 = new Organization("Siemens AG");
        o7.addPosition("01/2005", "04/2005", "3 месяца обучения мобильным IN сетям (Берлин)", "");
        eduSection.addOrganization(o7);

        Organization o8 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        o8.addPosition("09/1993", "07/1996", "Аспирантура (программист С, С++)", "");
        o8.addPosition("09/1987", "07/1993", "Инженер (программист Fortran, C)", "");
        eduSection.addOrganization(o8);
        resume1.setSectionData(SectionType.EDUCATION, eduSection);
    }

    @Test
    public void contactAdd() {
        System.out.println(resume1.toString());
    }

    public void printSectionData(String[] data) {
        for (String s : data) {
            System.out.println(s);
        }
    }
}
