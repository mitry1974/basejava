package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.web.webutils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateResumeAction implements ResumeAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception {
        String uuid = request.getParameter("uuid");
        String name = request.getParameter("fullName");

        final Resume r = storage.get(uuid);
        r.setFullName(name);
        WebUtils.fillResume(request, r);
        storage.update(r);
        response.sendRedirect(request.getContextPath() + "/resume");
    }
}
