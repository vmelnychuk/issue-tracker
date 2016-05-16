package server.issuetracker.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.db.DBService;
import server.issuetracker.db.DataBaseService;
import server.issuetracker.db.HibernateDBService;
import server.issuetracker.repository.UserRepository;
import server.issuetracker.repository.UserRepositoryJdbc;
import server.issuetracker.service.*;
import server.issuetracker.web.servlet.AdminServlet;
import server.issuetracker.web.servlet.LoginServlet;
import server.issuetracker.web.servlet.RegistrationServlet;
import server.issuetracker.web.servlet.UserDashboard;

public class Main {
    private static final int PORT = 8080;
    private static final String LOGIN_FORM = "/login";
    private static final String REGISTER_FORM = "/register";
    private static final String ADMIN_PANE = "/admin";
    private static final String DASHBOARD = "/dash-board";

    private static final String STATIC_FILES = "static_files";

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DataBaseService dataBaseService = new HibernateDBService();
        dataBaseService.printConnectionInfo();

        UserRepository userRepository = new UserRepositoryJdbc(dataBaseService.getConnection());
        UserService userService = new UserServiceWithJDBC(userRepository);
        LoginService loginService = new LoginServiceWithCollection(userService);

        LoginServlet loginServlet = new LoginServlet(loginService);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService);
        AdminServlet adminServlet = new AdminServlet(userService);
        UserDashboard userDashboard = new UserDashboard(loginService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // servlet mapping
        context.addServlet(new ServletHolder(loginServlet), LOGIN_FORM);
        context.addServlet(new ServletHolder(registrationServlet), REGISTER_FORM);
        context.addServlet(new ServletHolder(adminServlet), ADMIN_PANE);
        context.addServlet(new ServletHolder(userDashboard), DASHBOARD);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_FILES);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceHandler, context });

        Server server = new Server(PORT);
        server.setHandler(handlers);

        try {
            log.info("starting Jetty at http://localhost:" + PORT);
            server.start();
            server.join();
        } catch (InterruptedException e) {
            log.error("server was interrupted", e);
        } catch (Exception e) {
            log.error("problems with server starting", e);
        }
    }
}
