package server.issuetracker.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import server.issuetracker.bean.user.User;
import server.issuetracker.service.UserService;

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String ACTION = "action";
    
    private final UserService userService;
    
    public AdminServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
        action = action == null? "" : action;
        String responseString = "";
        switch (action) {
            case "allUsers":
                responseString = getAllUsers();
                break;
            default:
                break;
        }
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = resp.getWriter();
        writer.println(responseString);
    }
    
    private String getAllUsers() {
        Gson gson = new Gson();
        Collection<User> users = userService.getAllUsers();
        String json = gson.toJson(users);
        return json;
    }

}
