package server.issuetracker.web.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.issuetracker.bean.user.User;
import server.issuetracker.service.LoginService;
import server.issuetracker.utils.Users;
import server.issuetracker.web.view.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_FORM = "login-form.ftl";
    private static final String WRONG_PASSWORD = "login-wrong-password.ftl";
    private static final String DASHBOARD = "/dash-board";

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    private final LoginService loginService;

    public LoginServlet(LoginService loginService) {
        this.loginService = loginService;
    }

    // login form
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        User user = loginService.getUserBySessionId(sessionId);
        PrintWriter writer = resp.getWriter();
        if(user == null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html;charset=utf-8");
            writer.println(PageGenerator.instance().getPage(LOGIN_FORM, null));
        } else {
            resp.sendRedirect(DASHBOARD);
        }
    }

    // login
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

        User user = loginService.checkLogin(login, password);
        if (user == Users.NULL_USER) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/html;charset=utf-8");
            writer.println(PageGenerator.instance().getPage(WRONG_PASSWORD, null));
            return;
        }

        loginService.logIn(req.getSession().getId(), user);
        resp.sendRedirect(DASHBOARD);
    }

    // logout
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        User user = loginService.getUserBySessionId(sessionId);
        if(user == null) {
            log.info("user is not loggined");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("text/plain;charset=utf-8");
        } else {
            loginService.logOut(sessionId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain;charset=utf-8");
        }
    }
    
    
}
