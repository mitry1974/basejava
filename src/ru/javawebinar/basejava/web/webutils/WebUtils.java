package ru.javawebinar.basejava.web.webutils;

import ru.javawebinar.basejava.model.*;

import javax.servlet.http.HttpServletRequest;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class WebUtils {
    public static Resume makeEmptyResume() {
        Resume r = new Resume("Введите имя");
        Section section = null;
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection("");
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    section = new ListSection(new String[0]);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> orgs = new ArrayList<>();
                    orgs.add(new Organization("", "", new Organization.Position()));
                    section = new OrganizationSection(orgs);
                    break;

            }
            r.addSection(type, section);
        }
        return r;

    }

    public static void fillResume(HttpServletRequest request, Resume r) {
        String name = request.getParameter("fullName");
        r.setFullName(name);

        for (ContactType t : ContactType.values()) {
            String value = request.getParameter(t.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(t, value);
            } else {
                r.getContacts().remove(t);
            }
        }

        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(type, new TextSection(request.getParameter(type.name())));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(type, new ListSection(String.join("\n", request.getParameterValues(type.name()))));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganizationSection section = new OrganizationSection();

                    String[] names = request.getParameterValues(type.name());

                    List<Organization> organizations = new ArrayList<>();
                    section.setOrganizations(organizations);

                    if (names != null) {
                        for (int i = 0; i < names.length; i++) {
                            List<Organization.Position> positions = new ArrayList<>();

                            String orgName = names[i];
                            String part = type.name() + '_' + i;
                            String orgUrl = request.getParameter(part + "_url");
                            String[] sdate = request.getParameterValues(part + "_sdate");
                            String[] fdate = request.getParameterValues(part + "_fdate");
                            String[] title = request.getParameterValues(part + "_title");
                            String[] descr = request.getParameterValues(part + "_descr");

                            for (int j = 0; j < sdate.length; j++) {

                                Organization.Position position = new Organization.Position(YearMonth.parse(sdate[j]), YearMonth.parse(fdate[j]), title[j], descr[j]);
                                positions.add(position);
                            }

                            organizations.add(new Organization(new Link(orgName, orgUrl), positions));
                        }
                    }

                    r.addSection(type, section);
                    break;
            }
        }
    }

    public static String toHtml(SectionType type, Section section) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<h2>");
        sb.append(type.getTitle());
        sb.append("</h2>");
        sb.append("<td>");
        sb.append("<tr>");
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                sb.append("<h3>");
                TextSection ts = (TextSection) section;
                sb.append(ts.getContent());
                sb.append("</h3>");
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = (ListSection) section;
                for (String s : ls.getItems()) {
                    sb.append("<ul><li>");
                    sb.append(s);
                    sb.append("</li></ul>");
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                OrganizationSection os = (OrganizationSection) section;
                for (Organization o : os.getOrganizations()) {
                    sb.append("<h3><a href=\"");
                    sb.append(o.getHomePage().getUrl());
                    sb.append("\">");
                    sb.append(o.getHomePage().getName());
                    sb.append("</a></h3>");
                    for (Organization.Position p : o.getPositions()) {
                        sb.append(p.getStartDate());
                        sb.append(" - ");
                        sb.append(p.getEndDate());
                        sb.append("\t");
                        sb.append("<b>");
                        sb.append(p.getTitle());
                        sb.append("</b>");
                        sb.append("</br>");
                        sb.append(p.getDescription());

                    }
                }
                break;
        }
        sb.append("</tr>");
        sb.append("</td>");
        return sb.toString();
    }
}
