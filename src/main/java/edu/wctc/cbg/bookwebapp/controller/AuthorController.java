package edu.wctc.cbg.bookwebapp.controller;


import edu.wctc.cbg.bookwebapp.entity.Author;
import edu.wctc.cbg.bookwebapp.entity.Book;
import edu.wctc.cbg.bookwebapp.service.AuthorService;
import edu.wctc.cbg.bookwebapp.service.BookService;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Chris Gonzalez 2017
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/ac"})
public class AuthorController extends HttpServlet {
    
    private AuthorService authorService;
    private BookService bookService;
    
    public static final String ERROR_INVALID_PARAM = "ERROR: Invalid Parameter";
    
    private static final String ATTR_SESSION_NUMBER_CHANGES = "sessionChanges";
    
    /*pages*/
    private static final String ERROR_PAGE = "/errorPage.jsp"; 
    private static final String HOME_PAGE = "index.jsp";
    private static final String AUTHOR_LIST_PAGE = "/authorList.jsp";
    private static final String ADD_EDIT_AUTHOR_PAGE = "/addEditAuthor.jsp";
    
    /*authors list attribute set by this controller class*/
    private static final String AUTHOR_LIST_ATTRIBUTE = "authors";
    /*book list attribute*/
    private static final String BOOKS_LIST_ATTRIBUTE = "books";
    
    /*attribute's name used to indicate request type*/
    private static final String REQUEST_TYPE = "rType";
    /*List of special request types that indicate an action to perform*/
    private static final String RTYPE_AUTHOR_LIST = "authorList";
    private static final String RTYPE_HOME = "home";
    private static final String RTYPE_DELETE_AUTHOR = "deleteAuthor";
    private static final String RTYPE_ADD_AUTHOR = "addAuthor";
    private static final String RTYPE_EDIT_AUTHOR = "editAuthor";
    private static final String RTYPE_SAVE_AUTHOR = "saveAuthor";
    
    /*Html check box used to determine what author to delete*/
    private static final String CHECKBOX_NAME_AUTHOR_ID = "authorId";
    /*attribute sent through a query string to indicate which author to edit
        info for*/
    private static final String AUTHOR_ID_TO_EDIT = "id";
    /*Attribute name shown in Author list page to display number of updates
    made in list for the session/ throughout app*/
    private static final String NUMBER_HITS_SESSION = "hitsSession";
    private static final String NUMBER_HITS_APP = "hitsApp";
    
    /*Html text inputs that display a specified author info*/
    private static final String INPUT_AUTHOR_ID = "authorId";
    private static final String INPUT_AUTHOR_NAME = "authorName";
    private static final String INPUT_DATE_ADDED = "dateAdded";
    
    /**
     * To be used to redirect users to Author list page. Prevents resubmission
     * after refresh
     */
    private static final String AUTHOR_LIST_REQUEST = "ac?rType=authorList";
    
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
            
            if(requestType.equalsIgnoreCase(RTYPE_AUTHOR_LIST)){
                destination = AUTHOR_LIST_PAGE;
                List<Author> authors = authorService.findAll();
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_HOME)){
                /*Instead of dispatching a request object*/
                 response.sendRedirect(response.encodeRedirectURL(HOME_PAGE));
                 return;
            }else if(requestType.equalsIgnoreCase(RTYPE_DELETE_AUTHOR)){
                String[] authorsToDelete = request.getParameterValues(CHECKBOX_NAME_AUTHOR_ID);
                if(authorsToDelete != null){
                    for(String id : authorsToDelete){
                        authorService.removeById(id);
                    }
                    this.addToChangesMade(session);
                }
                
                response.sendRedirect(response.encodeURL(AUTHOR_LIST_REQUEST));
                return;
            }else if(requestType.equalsIgnoreCase(RTYPE_ADD_AUTHOR)){
                destination = ADD_EDIT_AUTHOR_PAGE;
            }else if(requestType.equalsIgnoreCase(RTYPE_EDIT_AUTHOR)){
                destination = ADD_EDIT_AUTHOR_PAGE;
                /*If we are editing customer information, we must retrieve the id of the 
                author selected, based on an attribute created by a query string*/
                String id  = request.getParameter(AUTHOR_ID_TO_EDIT);
                Author author = authorService.findById(id);
                request.setAttribute(INPUT_AUTHOR_ID, author.getAuthorId());
                request.setAttribute(INPUT_AUTHOR_NAME, author.getAuthorName());
                request.setAttribute(INPUT_DATE_ADDED, author.getDateAdded());
                
                List<Book> books = bookService.getBooksByAuthorIdAlphabetically(id);
                request.setAttribute(BOOKS_LIST_ATTRIBUTE, books);
            }else if(requestType.equalsIgnoreCase(RTYPE_SAVE_AUTHOR)){
                String authorName = request.getParameter(INPUT_AUTHOR_NAME);
                String id = request.getParameter(INPUT_AUTHOR_ID);
                
                /*Test to check to see if authorName is null or empty. If it is, then the controller
                will simply redirect the user to the same page (addEditAuthor)*/
                if(authorName != null && !authorName.isEmpty()){
                    authorService.saveOrEdit(id, authorName);
                    this.addToChangesMade(session);
                    response.sendRedirect(response.encodeURL(AUTHOR_LIST_REQUEST));
                    return;
                }else{
                    if(id != null && !id.isEmpty()){
                        Author author = authorService.findById(id);
                        request.setAttribute(INPUT_AUTHOR_ID, author.getAuthorId());
                        request.setAttribute(INPUT_AUTHOR_NAME, author.getAuthorName());
                        request.setAttribute(INPUT_DATE_ADDED, author.getDateAdded());
                        
                        List<Book> books = bookService.getBooksByAuthorIdAlphabetically(id);
                        request.setAttribute(BOOKS_LIST_ATTRIBUTE, books);
                    }
                    destination = ADD_EDIT_AUTHOR_PAGE;
                }
            }else{
                request.setAttribute("errorMsg", ERROR_INVALID_PARAM);
            }
        } catch (Exception e) {
            destination = ERROR_PAGE;
            e.printStackTrace();
            request.setAttribute("errorMsg", e.getCause().getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher(response.encodeURL(destination));
        dispatcher.forward(request, response);
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
    private void addToChangesMade(HttpSession session) {
        Object sessionChanges = session.getAttribute(ATTR_SESSION_NUMBER_CHANGES);
        if (sessionChanges != null) {
            session.setAttribute(ATTR_SESSION_NUMBER_CHANGES, Integer.parseInt(sessionChanges.toString()) + 1);
        } else {
            session.setAttribute(ATTR_SESSION_NUMBER_CHANGES, 1);
        }
    }
    @Override
    public final void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authorService = (AuthorService) ctx.getBean("authorService");
        bookService = (BookService) ctx.getBean("bookService");
    }
}
