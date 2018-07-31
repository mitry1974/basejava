package ru.javawebinar.basejava.web;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ResumeActionFactory {
    private static final ResumeActionFactory instance = new ResumeActionFactory();
    private final static HashMap<String, ResumeAction> actions = new HashMap<>();

    static {
        actions.put("POST/updateResume", new UpdateResumeAction());
        actions.put("POST/saveResume", new SaveResumeAction());
    }

    public static ResumeAction getAction(HttpServletRequest request) {
        String path = request.getPathInfo();
        String req = request.getMethod() + (path == null ? "" : path);
        return actions.get(req);

    }

    public static ResumeActionFactory getInstance() {
        return instance;
    }
}
