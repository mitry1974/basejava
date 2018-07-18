package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditResumeAction implements ResumeAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception {
        String uuid = request.getParameter("uuid");
        request.setAttribute("resume", storage.get(uuid));
        request.setAttribute("resumeAction", "resume/updateResume");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }
}
