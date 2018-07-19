package ru.javawebinar.basejava.web.webutils;

import ru.javawebinar.basejava.model.*;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    public static void fillResume(HttpServletRequest request, Resume r) {
        for (ContactType t : ContactType.values()) {
            String value = request.getParameter(t.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(t, value);
            } else {
                r.getContacts().remove(t);
            }
        }
    }

    public static String toHtml(SectionType type, Section section){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<h2>");
        sb.append(type.getTitle());
        sb.append("</h2>");
        sb.append("<td>");
        sb.append("<tr>");
        switch(type){
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
                for(String s:ls.getItems()){
                    sb.append("<ul><li>");
                    sb.append(s);
                    sb.append("</li></ul>");
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                OrganizationSection os = (OrganizationSection) section;
                for(Organization o : os.getOrganizations()){
                    sb.append("<h3><a href=\"");
                    sb.append(o.getHomePage().getUrl());
                    sb.append("\">");
                    sb.append(o.getHomePage().getName());
                    sb.append("</a></h3>");
                    for(Organization.Position p : o.getPositions()){
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
