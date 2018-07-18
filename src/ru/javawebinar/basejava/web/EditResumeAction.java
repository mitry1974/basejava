package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditResumeAction implements ResumeAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception {
        String uuid = request.getParameter("uuid");
        Resume r = storage.get(uuid);
        request.setAttribute("resume", r);
        request.setAttribute("resumeAction", "resume/updateResume");
        String pInfo = request.getPathInfo();
        String sPath = request.getServletPath();
        String cPath = request.getContextPath();

        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }
}
