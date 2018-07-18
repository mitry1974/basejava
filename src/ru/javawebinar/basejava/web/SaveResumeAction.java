package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.web.webutils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveResumeAction implements ResumeAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception {
        String name = request.getParameter("fullName");
        final Resume r = new Resume(name);
        WebUtils.fillResume(request, r);
        storage.save(r);
        response.sendRedirect(request.getContextPath() + "/resume");
    }
}
