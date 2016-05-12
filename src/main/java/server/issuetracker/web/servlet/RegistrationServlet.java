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
import server.issuetracker.utils.Users;
import server.issuetracker.web.view.PageGenerator;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String REGISTER_FORM = "register-form.ftl";

    private static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);

    private final UserService userService;

    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");
        writer.println(PageGenerator.instance().getPage(REGISTER_FORM, null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = parseUserRequest(req);

        if (user == Users.NULL_USER) {
            log.info("bad request");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        log.info("user creation");
        userService.addUser(user);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = resp.getWriter();
        writer.println(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = parseUserRequest(req);
        User oldUser = userService.getUserByLogin(req.getParameter(LOGIN));
        oldUser.update(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        userService.remove(login);
    }

    private User parseUserRequest(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);

        if (login == null || password == null || firstName == null || lastName == null) {
            return Users.NULL_USER;
        } else {
            return Users.createUser(login, password, firstName, lastName);
        }
    }
}
