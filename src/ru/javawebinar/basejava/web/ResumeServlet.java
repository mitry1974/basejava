package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.web.webutils.WebUtils;

import javax.servlet.ServletException;
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
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        String actionString = "";
        String uuid = req.getParameter("uuid");
        Resume r = null;

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
            case "add":
                actionString = "/WEB-INF/jsp/edit.jsp";
                r = WebUtils.makeEmptyResume();
                req.setAttribute("resumeAction", "saveResume");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        if (r != null) {
            req.setAttribute("resume", r);
        }
        req.getRequestDispatcher(actionString).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final Resume r;
        boolean bUpdate = req.getMethod().equals("updateResume");

        if(bUpdate){
            String uuid = req.getParameter("uuid");
            r = storage.get(uuid);
        }
        else{
            String name = req.getParameter("fullName");
            r = new Resume(name);
        }

        WebUtils.fillResume(req, r);

        if(bUpdate){
            storage.update(r);

        }else{
            storage.save(r);
        }
        resp.sendRedirect(req.getContextPath() + "/resume");
    }
}
