package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    private Storage sqlStorage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if(action == null){
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        String actionString = "";
        String uuid = req.getParameter("uuid");
        Resume r = null;
        ResumeAction ra = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            case "view":
                actionString = "/WEB-INF/jsp/view.jsp";
                r = storage.get(uuid);
                break;
            case "edit":
                actionString = "/WEB-INF/jsp/edit.jsp";
                r = storage.get(uuid);
                req.setAttribute("resumeAction", "updateResume");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", r);
        req.getRequestDispatcher(actionString).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ResumeAction ra = ResumeActionFactory.getAction(req);
        callAction(ra, req, resp);
    }

    private void callAction(ResumeAction ra, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            if (ra != null) {
                ra.execute(request, response, storage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeResume(StringBuilder sb, Resume r) throws IOException {
        sb.append("<tr><td>");
        sb.append(r.getFullName());
        sb.append("</tr></td>");
    }
}
