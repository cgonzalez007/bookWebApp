package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.model.Author;
import edu.wctc.cbg.bookwebapp.model.AuthorService;
import edu.wctc.cbg.bookwebapp.model.DbAccessor;
import edu.wctc.cbg.bookwebapp.model.IAuthorDao;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Chris Gonzalez 2017
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/ac"})
public class AuthorController extends HttpServlet {
    public static final String ERROR_INVALID_PARAM = "ERROR: Invalid Parameter";
    
    /*pages*/
    public static final String HOME_PAGE = "index.jsp";
    public static final String AUTHOR_LIST_PAGE = "/authorList.jsp";
    public static final String ADD_EDIT_AUTHOR_PAGE = "/addEditAuthor.jsp";
    
    /*authors list attribute set by this controller class*/
    public static final String AUTHOR_LIST_ATTRIBUTE = "authors";
    
    /*attribute's name used to indicate request type*/
    public static final String REQUEST_TYPE = "rType";
    /*List of special request types that indicate an action to perform*/
    public static final String RTYPE_AUTHOR_LIST = "authorList";
    public static final String RTYPE_HOME = "home";
    public static final String RTYPE_DELETE_AUTHOR = "deleteAuthor";
    public static final String RTYPE_ADD_AUTHOR = "addAuthor";
    public static final String RTYPE_EDIT_AUTHOR = "editAuthor";
    public static final String RTYPE_SAVE_AUTHOR = "saveAuthor";
    
    /*Html check box used to determine what author to delete*/
    public static final String CHECKBOX_NAME_AUTHOR_ID = "authorId";
    /*attribute sent through a query string to indicate which author to edit
        info for*/
    public static final String AUTHOR_ID_TO_EDIT = "id";
    /*Attribute name shown in Author list page to display number of updates
    made in list for the session/ throughout app*/
    public static final String NUMBER_HITS_SESSION = "hitsSession";
    public static final String NUMBER_HITS_APP = "hitsApp";
    
    /*Html text inputs that display a specified author info*/
    public static final String INPUT_AUTHOR_ID = "authorId";
    public static final String INPUT_AUTHOR_NAME = "authorName";
    public static final String INPUT_DATE_ADDED = "dateAdded";
    
    /**
     * Used to Detect valid submits (Mainly to prevent form submission that 
     * adds a new author)
     */
    public static final String VALID_SUBMIT = "validAddAuthor";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    /**
     * Db info
     */
    public static final String AUTHOR_TABLE_NAME = "author";
    public static final String AUTHOR_ID_COL_NAME = "author_id";
    public static final String AUTHOR_NAME_COL_NAME = "author_name";
    public static final String DATE_ADDED_COL_NAME = "date_added";
    public static final int MAX_RECORDS = 50;
    
    /*used for setting current datetime when adding new author to db*/
    private LocalDateTime currentDate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    //Get init parameters from web.xml
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private String dbAccessorClassName;
    private String daoClassName;
    private String jndiName;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String requestType = request.getParameter(REQUEST_TYPE);
        String destination = HOME_PAGE;
        try {
            HttpSession session = request.getSession();
            Object sessHits = session.getAttribute(NUMBER_HITS_SESSION);
            if(sessHits != null){
                session.setAttribute(NUMBER_HITS_SESSION, Integer.parseInt(sessHits.toString())+1);
            }else{
                session.setAttribute(NUMBER_HITS_SESSION,1);
            }
            
            ServletContext ctx = request.getServletContext();
            synchronized(ctx) {
                Object appHits = ctx.getAttribute(NUMBER_HITS_APP);
                if(appHits != null){
                   ctx.setAttribute(NUMBER_HITS_APP, Integer.parseInt(appHits.toString())+1);
                }else{
                   ctx.setAttribute(NUMBER_HITS_APP,1);
                }
            }
            
            AuthorService authorService = injectDependenciesAndGetAuthorService();
            
            if(requestType.equalsIgnoreCase(RTYPE_AUTHOR_LIST)){
                destination = AUTHOR_LIST_PAGE;
                List<Author> authors = authorService.retrieveAuthors(
                        AUTHOR_TABLE_NAME, MAX_RECORDS);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_HOME)){
                /*Instead of dispatching a request object*/
                 response.sendRedirect(response.encodeRedirectURL(HOME_PAGE));
                 return;
            }else if(requestType.equalsIgnoreCase(RTYPE_DELETE_AUTHOR)){
                destination = AUTHOR_LIST_PAGE;
                String[] authorsToDelete = request.getParameterValues(CHECKBOX_NAME_AUTHOR_ID);
                if(authorsToDelete != null){
                    for(String id : authorsToDelete){
                        authorService.deleteAuthorById(AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, id);
                    }
                }
                refreshResults(request, authorService);
            }else if(requestType.equalsIgnoreCase(RTYPE_ADD_AUTHOR)){
                destination = ADD_EDIT_AUTHOR_PAGE;
                request.setAttribute(VALID_SUBMIT, TRUE);
            }else if(requestType.equalsIgnoreCase(RTYPE_EDIT_AUTHOR)){
                destination = ADD_EDIT_AUTHOR_PAGE;
                /*If we are editing customer information, we must retrieve the id of the 
                author selected, based on an attribute created by a query string*/
                String id  = request.getParameter(AUTHOR_ID_TO_EDIT);
                Author author = authorService.retrieveAuthor(AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, id);
                request.setAttribute(INPUT_AUTHOR_ID, author.getAuthorId());
                request.setAttribute(INPUT_AUTHOR_NAME, author.getAuthorName());
                request.setAttribute(INPUT_DATE_ADDED, author.getDateAdded());
            }else if(requestType.equalsIgnoreCase(RTYPE_SAVE_AUTHOR)){
                destination = AUTHOR_LIST_PAGE; 
                String authorName = request.getParameter(INPUT_AUTHOR_NAME);
                String id = request.getParameter(INPUT_AUTHOR_ID);
                
                /*Test to check to see if authorName is null or empty. If it is, then the controller
                will simply redirect the user to the same page (addEditAuthor)*/
                if(authorName != null && !authorName.isEmpty()){
                    /*If id is null/empty, that means we are not referencing an author 
                    already in the database, so we take the name entered, and current
                    date to add a new author in the database. Otherwise if id does 
                    contain a value, that means we are simply editing author information
                    so the name simply gets updated in the database.*/
                    if(id == null || id.isEmpty()){
                        currentDate = LocalDateTime.now();

                        List<String> colNames = new ArrayList<>();
                        colNames.add(AUTHOR_NAME_COL_NAME);
                        colNames.add(DATE_ADDED_COL_NAME);
                        List<Object> colValues = new ArrayList<>();
                        colValues.add(authorName);
                        colValues.add(DATE_TIME_FORMATTER.format(currentDate));

                        authorService.addNewAuthor(AUTHOR_TABLE_NAME, 
                                colNames, colValues);
                        
                    }else{                    
                        List<String> colNames = new ArrayList<>();
                        colNames.add(AUTHOR_NAME_COL_NAME);
                        List<Object> colValues = new ArrayList<>();
                        colValues.add(authorName);
                        authorService.updateAuthorById(AUTHOR_TABLE_NAME, colNames, 
                                    colValues, AUTHOR_ID_COL_NAME, id);
                    }
                    refreshResults(request, authorService);
                }else{
                    if(id != null && !id.isEmpty()){
                        Author author = authorService.retrieveAuthor(AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, id);
                        request.setAttribute(INPUT_AUTHOR_ID, author.getAuthorId());
                        request.setAttribute(INPUT_AUTHOR_NAME, author.getAuthorName());
                        request.setAttribute(INPUT_DATE_ADDED, author.getDateAdded());
                    }
                    destination = ADD_EDIT_AUTHOR_PAGE;
                }
            }else{
                request.setAttribute("errMsg", ERROR_INVALID_PARAM);
            }
        } catch (Exception e) {
            destination = HOME_PAGE;
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher(response.encodeURL(destination));
        dispatcher.forward(request, response);
    }
    
     /*
        This helper method just makes the code more modular and readable.
        It's single responsibility principle for a method.
    */
    private AuthorService injectDependenciesAndGetAuthorService() throws Exception {
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DBStrategy based on the class name retrieved
        // from web.xml
        Class dbClass = Class.forName(dbAccessorClassName);
        // Use Java reflection to instanntiate the DBStrategy object
        // Note that DBStrategy classes have no constructor params
        DbAccessor db = (DbAccessor) dbClass.newInstance();

        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        IAuthorDao authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        
        // This will only work for the non-pooled AuthorDao
        try {
            constructor = daoClass.getConstructor(new Class[]{
                DbAccessor.class, String.class, String.class, String.class, String.class
            });
        } catch(NoSuchMethodException nsme) {
            // do nothing, the exception means that there is no such constructor,
            // so code will continue executing below
        }

        // constructor will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
        
        if (constructor != null) {
            // conn pool NOT used so constructor has these arguments
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, username, password
            };
            authorDao = (IAuthorDao) constructor
                    .newInstance(constructorArgs);

        } else {
            /*
             Here's what the connection pool version looks like. First
             we lookup the JNDI name of the Glassfish connection pool
             and then we use Java Refletion to create the needed
             objects based on the servlet init params
             */
            Context ctx = new InitialContext();
            /*For PCs ONLY*/
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            /*For Macs ONLY*/
//            Context envCtx = (Context) ctx.lookup("java:comp/env");
//            DataSource ds = (DataSource) envCtx.lookup(jndiName);
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbAccessor.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            authorDao = (IAuthorDao) constructor
                    .newInstance(constructorArgs);
        }
        
        return new AuthorService(authorDao);
    }

    
    private void refreshResults(HttpServletRequest request, AuthorService authorService) 
            throws ClassNotFoundException, SQLException{
        List<Author> authors = authorService.retrieveAuthors(
                        AUTHOR_TABLE_NAME, MAX_RECORDS);
        request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authors);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public final void init() throws ServletException {
        driverClass = getServletContext().getInitParameter("driverClass");
        url = getServletContext().getInitParameter("url");
        username = getServletContext().getInitParameter("username");
        password = getServletContext().getInitParameter("password");
        dbAccessorClassName = getServletContext().getInitParameter("dbStrategy");
        daoClassName = getServletContext().getInitParameter("authorDao");
        jndiName = getServletContext().getInitParameter("connPoolName");
    }
}
