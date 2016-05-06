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

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet call");
        Map<String, Object> params = requestVaraibles(req);
        
        PrintWriter writer = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");
        writer.println(PageGenerator.instance().getPage("page-template.ftl", params));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
    private Map<String, Object> requestVaraibles(HttpServletRequest req) {
        Map<String, Object> pageVars = new HashMap<>();
        pageVars.put("method", req.getMethod());
        pageVars.put("url", req.getRequestURL().toString());
        pageVars.put("params", req.getParameterMap().toString());
        pageVars.put("session", req.getSession().getId());
        return pageVars;
    }
}
