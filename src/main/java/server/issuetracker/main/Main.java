package server.issuetracker.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.service.UserService;
import server.issuetracker.service.UserServiceWithCollection;
import server.issuetracker.web.servlet.AdminServlet;
import server.issuetracker.web.servlet.LoginServlet;
import server.issuetracker.web.servlet.RegistrationServlet;

public class Main {
    private static final int PORT = 8080;
    private static final String LOGIN_FORM = "/login";
    private static final String REGISTER_FORM = "/register";
    private static final String ADMIN_PANE = "/admin";

    private static final String STATIC_FILES = "static_files";

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LoginServlet loginServlet = new LoginServlet();

        UserService userService = new UserServiceWithCollection();

        RegistrationServlet registrationServlet = new RegistrationServlet(userService);
        AdminServlet adminServlet = new AdminServlet(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // servlet mapping
        context.addServlet(new ServletHolder(loginServlet), LOGIN_FORM);
        context.addServlet(new ServletHolder(registrationServlet), REGISTER_FORM);
        context.addServlet(new ServletHolder(adminServlet), ADMIN_PANE);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_FILES);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceHandler, context });

        Server server = new Server(PORT);
        server.setHandler(handlers);

        try {
            log.info("starting Jetty");
            server.start();
            server.join();
        } catch (InterruptedException e) {
            log.error("server was interrupted", e);
        } catch (Exception e) {
            log.error("problems with server starting", e);
        }
    }
}
