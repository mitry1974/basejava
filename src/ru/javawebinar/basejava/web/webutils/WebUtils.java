package ru.javawebinar.basejava.web.webutils;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

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
}
