package server.issuetracker.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.web.view.PageGenerator;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String REGISTER_FORM = "register-form.ftl";
    private static final String REGISTER_PAGE = "register-page.ftl";
    
    private static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet call");
        
        PrintWriter writer = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");
        writer.println(PageGenerator.instance().getPage(REGISTER_FORM, null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost call");
        
        Map<String, Object> data = parseRequest(req);
        PrintWriter writer = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");
        writer.println(PageGenerator.instance().getPage(REGISTER_PAGE, data));
    }
    
    private Map<String, Object> parseRequest(HttpServletRequest req) {
        Map<String, Object> pageVars = new HashMap<>();
        pageVars.put(LOGIN, req.getParameter(LOGIN));
        pageVars.put(PASSWORD, req.getParameter(PASSWORD));
        pageVars.put(FIRST_NAME, req.getParameter(FIRST_NAME));
        pageVars.put(LAST_NAME, req.getParameter(LAST_NAME));
        return pageVars;
    }
}
