package edu.wctc.cbg.bookwebapp.listeners;

import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author cgonz
 */
public class CustomServletContextListener implements ServletContextListener {
    private static final String DATE_INITIALIZED_ATTRIBUTE = "dateInitialized";
    @Override
    public final void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        Date date = new Date();
        sc.setAttribute(DATE_INITIALIZED_ATTRIBUTE, date);
    }

    @Override
    public final void contextDestroyed(ServletContextEvent sce) {
        // Does nothing currently
    }
    
}
