package edu.wctc.cbg.bookwebapp.listeners;

import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author cgonz
 */
public class CustomSessionListener implements HttpSessionListener{
    private static final String DATE_SESS_STARTED_ATTRIBUTE = "dateSessStarted";
    @Override
    public final void sessionCreated(HttpSessionEvent se) {
        HttpSession sess = se.getSession();
        Date date = new Date();
        sess.setAttribute(DATE_SESS_STARTED_ATTRIBUTE, date);
    }

    @Override
    public final void sessionDestroyed(HttpSessionEvent se) {
        // Does nothing currently
    }
    
}
