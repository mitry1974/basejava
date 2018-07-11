package web;

import exception.NotExistStorageException;
import model.Resume;
import storage.Storage;
import util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage sqlStorage = Config.get().getStorage();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("doPost!!!!!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        StringBuilder sb = new StringBuilder();
        if (uuid != null) {
            try {
                Resume r = sqlStorage.get(uuid);
                writeResume(sb, r);
            } catch (NotExistStorageException e) {
                response.getWriter().write("Нет! такого имени");
            }
        } else {
            List<Resume> resumes = sqlStorage.getAllSorted();
            sb.append("<table>\n");
            sb.append("<tr><th>РЕЗЮМЕ</th></tr>\n");
            for (Resume r : resumes) {
                writeResume(sb, r);
            }
            sb.append("</table>\n");
        }
        response.getWriter().write(sb.toString());
    }

    private void writeResume(StringBuilder sb, Resume r) throws IOException {
        sb.append("<tr><td>");
        sb.append(r.getFullName());
        sb.append("</tr></td>");
    }
}
