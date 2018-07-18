package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResumeAction {
    public void execute(HttpServletRequest request, HttpServletResponse response, Storage storage) throws Exception;
}
