package server.issuetracker.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.issuetracker.web.servlet.AuthServlet;

public class Main {
    private static final int PORT = 8080;
    private static final String AUTH_FORM = "/authform";
    
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        AuthServlet authServlet = new AuthServlet();

        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(authServlet), AUTH_FORM);
        
        try {
            log.info("starting Jetty");
            server.start();
            server.join();
        } catch(InterruptedException e) {
            log.error("server was interrupted");
        } catch (Exception e) {
            log.error("problems with server starting");
        }
    }
}
