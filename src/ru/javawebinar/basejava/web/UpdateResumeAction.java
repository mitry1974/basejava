package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.web.webutils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class UpdateResumeAction implements ResumeAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception {
        String uuid = request.getParameter("uuid");
        String name = request.getParameter("fullName");
        final Resume r = storage.get(uuid);
        r.setFullName(name);

        for (ContactType t : ContactType.values()) {
            r.addContact(t, request.getParameter(t.name()));
        }

        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(type, new TextSection(request.getParameter(type.name())));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(type, new ListSection(
                            Arrays.asList(request.getParameterValues(
                                    type.name()))));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    String[] names = request.getParameterValues(type.name() + "_organizationName");
                    String[] urls = request.getParameterValues(type.name() + "_organizationUrl");

                    for (int i = 0; i < names.length; i++) {
                        String orgName = names[i];
                        String orgUrl = urls[i];


                    }
                    break;
            }
        }

        WebUtils.fillResume(request, r);
        storage.update(r);
        response.sendRedirect(request.getContextPath() + "/resume");
    }
}
