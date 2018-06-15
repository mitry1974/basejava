package storage;

import model.Contact;
import model.ContactType;
import model.Resume;
import model.SectionType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ResumeTest {

    Storage storage = new ListStorage();

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID1, "Жугдэрдэмидийн Гуррагча");
    private static final Resume resume2 = new Resume(UUID2, "Пеьр Петров");
    private static final Resume resume2_1 = new Resume(UUID2, "Сидор Сидоров");
    private static final Resume resume3 = new Resume(UUID3, "Александр Пушкин");
    private static final Resume resume4 = new Resume(UUID4, "Михаил Лермонтов");


    public ResumeTest() {
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);

        resume1.addContact("phone1", ContactType.PHONE, "+72326675345");
        resume1.addContact("mail1", ContactType.EMAIL, "test@test.ru");
        resume1.addContact("skype1", ContactType.SKYPE, "skype.contact");

        resume1.setSectionData(SectionType.PERSONAL, new String[]{"Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."});
        resume1.setSectionData(SectionType.ACHIEVEMENT, new String[]{
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        });
        resume1.setSectionData(SectionType.OBJECTIVE, new String[]{"Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"});
        resume1.setSectionData(SectionType.QUALIFICATIONS, new String[]{
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
        resume1.setSectionData(SectionType.EXPERIENCE, new String[]{
                "Java Online Projects\n" +
                        "10/2013 - Сейчас\tАвтор проекта.Создание, организация и проведение Java онлайн проектов и стажировок.\n",
                "Wrike\n" +
                        "10/2014 - 01/2016\tСтарший разработчик (backend)\n" +
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.\n",
                "RIT Center\n" +
                        "04/2012 - 10/2014\tJava архитектор\n" +
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python\n",
                "Luxoft (Deutsche Bank)\n" +
                        "12/2010 - 04/2012\tВедущий программист\n" +
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.\n",
                "Yota\n" +
                        "06/2008 - 12/2010\tВедущий специалист\n" +
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)\n",
                "Enkata\n" +
                        "03/2007 - 06/2008\tРазработчик ПО\n" +
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).\n",
                "Siemens AG\n" +
                        "01/2005 - 02/2007\tРазработчик ПО\n" +
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).\n",
                "Alcatel\n" +
                        "09/1997 - 01/2005\tИнженер по аппаратному и программному тестированию\n" +
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."});
        resume1.setSectionData(SectionType.EDUCATION, new String[]{"Coursera\n" +
                "03/2013 - 05/2013\t\"Functional Programming Principles in Scala\" by Martin Odersky\n",
                "Luxoft\n" +
                        "03/2011 - 04/2011\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"\n",
                "Siemens AG\n" +
                        "01/2005 - 04/2005\t3 месяца обучения мобильным IN сетям (Берлин)\n",
                "Alcatel\n" +
                        "09/1997 - 03/1998\t6 месяцев обучения цифровым телефонным сетям (Москва)\n",
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики\n" +
                        "09/1993 - 07/1996\tАспирантура (программист С, С++)\n" +
                        "09/1987 - 07/1993\tИнженер (программист Fortran, C)\n",
                "Заочная физико-техническая школа при МФТИ\n" +
                        "09/1984 - 06/1987\tЗакончил с отличием"});
    }

    @Test
    public void contactAdd() {

        System.out.println(resume1.getFullName());

        List<Contact> contacts = resume1.getContacts();
        for (Contact c : contacts) {
            System.out.println(c.printContactType() + ":" + c.printContactData());
        }

        System.out.println(resume1.getSectionTitle(SectionType.PERSONAL));
        printSectionData(resume1.getSectionData(SectionType.PERSONAL));
        System.out.println(resume1.getSectionTitle(SectionType.OBJECTIVE));
        printSectionData(resume1.getSectionData(SectionType.OBJECTIVE));
        System.out.println(resume1.getSectionTitle(SectionType.ACHIEVEMENT));
        printSectionData(resume1.getSectionData(SectionType.ACHIEVEMENT));
        System.out.println(resume1.getSectionTitle(SectionType.QUALIFICATIONS));
        printSectionData(resume1.getSectionData(SectionType.QUALIFICATIONS));
        System.out.println(resume1.getSectionTitle(SectionType.EXPERIENCE));
        printSectionData(resume1.getSectionData(SectionType.EXPERIENCE));
        System.out.println(resume1.getSectionTitle(SectionType.EDUCATION));
        printSectionData(resume1.getSectionData(SectionType.EDUCATION));
    }

    public void printSectionData(String[] data) {
        for (String s : data) {
            System.out.println(s);
        }
    }
}
