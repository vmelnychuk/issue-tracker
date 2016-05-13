package server.issuetracker.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import server.issuetracker.bean.user.User;
import server.issuetracker.service.UserService;
import server.issuetracker.web.view.PageGenerator;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_FORM = "login-form.ftl";
    private static final String WRONG_PASSWORD = "login-wrong-password.ftl";
    private static final String SITE = "/";

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    private final UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        User user = userService.getUserBySessionId(sessionId);
        PrintWriter writer = resp.getWriter();
        if(user == null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html;charset=utf-8");
            writer.println(PageGenerator.instance().getPage(LOGIN_FORM, null));
        } else {
            resp.sendRedirect(SITE);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        if (login == null || password == null) {
            log.info("not enough parameters");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        PrintWriter writer = resp.getWriter();

        User user = userService.getUserByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/html;charset=utf-8");
            writer.println(PageGenerator.instance().getPage(WRONG_PASSWORD, null));
            return;
        }

        userService.logIn(req.getSession().getId(), user);
        Gson gson = new Gson();
        String json = gson.toJson(user);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf-8");
        writer.println(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        User user = userService.getUserBySessionId(sessionId);
        if(user == null) {
            log.info("sessionId: " + sessionId);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("text/plain;charset=utf-8");
        } else {
            userService.logOut(sessionId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain;charset=utf-8");
        }
    }
    
    
}
