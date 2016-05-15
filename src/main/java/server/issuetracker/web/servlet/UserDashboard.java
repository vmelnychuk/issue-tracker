package server.issuetracker.web.servlet;

import server.issuetracker.bean.user.User;
import server.issuetracker.service.LoginService;
import server.issuetracker.web.view.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserDashboard extends HttpServlet {
    private static final String DASH_BOARD = "user-dash-board.ftl";

    private final LoginService loginService;

    public UserDashboard(LoginService loginService) {
        this.loginService = loginService;
    }

    // show user dashboard
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = loginService.getUserBySessionId(req.getSession().getId());

        PrintWriter writer = resp.getWriter();
        writer.println(PageGenerator.instance().getPage(DASH_BOARD, userToMap(user)));
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("login", user.getLogin());
        userMap.put("password", user.getPassword());
        userMap.put("first_name", user.getFirstName());
        userMap.put("last_name", user.getLastName());
        userMap.put("role", user.getRole().toString());
        return userMap;
    }
}
