package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    private Storage sqlStorage = Config.get().getStorage();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String name = request.getParameter("fullName");

        final Resume r = storage.get(uuid);
        r.setFullName(name);

        for(ContactType t : ContactType.values()){
            String value = request.getParameter(t.name());
            if(value != null && value.trim().length() != 0) {
                r.addContact(t, value);
            }
            else{
                r.getContacts().remove(t);
            }
        }

        storage.update(r);
        response.sendRedirect("resume");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r = null;
        String actionString = "";
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                actionString = "/WEB-INF/jsp/view.jsp";
                r = storage.get(uuid);
                break;
            case "edit":
                actionString = "/WEB-INF/jsp/edit.jsp";
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(actionString).forward(request, response);
    }

    private void writeResume(StringBuilder sb, Resume r) throws IOException {
        sb.append("<tr><td>");
        sb.append(r.getFullName());
        sb.append("</tr></td>");
    }
}
