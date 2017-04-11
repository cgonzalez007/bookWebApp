package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.model.Author;
import edu.wctc.cbg.bookwebapp.model.AuthorFacade;
import edu.wctc.cbg.bookwebapp.model.Book;
import edu.wctc.cbg.bookwebapp.model.BookFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cgonz
 */
@WebServlet(name = "BookController", urlPatterns = {"/bc"})
public class BookController extends HttpServlet {
    
    @EJB
    private AuthorFacade authorService;
    @EJB
    private BookFacade bookService;
    
    private static final String ATTR_SESSION_NUMBER_CHANGES = "sessionChanges";
    
    /*pages*/
    private static final String ERROR_PAGE = "/errorPage.jsp"; 
    private static final String HOME_PAGE = "index.jsp";
    private static final String BOOK_LIST_PAGE = "/bookList.jsp";
    private static final String ADD_EDIT_BOOK_PAGE = "/addEditBook.jsp";
    
    /**
     * To be used to redirect users to Author list page. Prevents resubmission
     * after refresh
     */
    private static final String BOOK_LIST_REQUEST = "bc?rType=bookList";
    
    /**
     * Attributes:
     */
    private static final String REQUEST_TYPE = "rType";
    private static final String AUTHORS = "authors";
    private static final String BOOKS = "books"; 
    
    /**
     * Request types:
     */
    private static final String RTYPE_HOME = "home";
    private static final String RTYPE_BOOK_LIST = "bookList";
    private static final String RTYPE_DELETE_BOOK = "deleteBook";
    private static final String RTYPE_ADD_BOOK = "addBook";
    private static final String RTYPE_EDIT_BOOK = "editBook";
    private static final String RTYPE_SAVE_BOOK = "saveBook";
    
    /*Html check box used to determine what author to delete*/
    private static final String CHECKBOX_NAME_BOOK_ID = "bookId";
    
    /*id name property of edit button to distinquish between books*/
    private static final String BOOK_ID_TO_EDIT = "id"; 
    
    /**
     * HTML input tag names for editBook page:
     */
    private static final String INPUT_BOOK_ID = "bookId";
    private static final String INPUT_TITLE = "title";
    private static final String INPUT_ISBN = "isbn";
    
    /**
     * AuthorId name for book:
     */
    private static final String AUTHOR_ID = "authorId";

    /**
     * HTML select tag name:
     */
    private static final String SELECT_AUTHORS = "authorSelect";
    
    private static final String ERROR_INVALID_PARAM = "ERROR: Invalid Parameter";
    
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
        
        try{
            HttpSession session = request.getSession();
            
            if(requestType.equalsIgnoreCase(RTYPE_HOME)){
                /*Instead of dispatching a request object*/
                 response.sendRedirect(response.encodeRedirectURL(HOME_PAGE));
                 return;
            }else if(requestType.equalsIgnoreCase(RTYPE_DELETE_BOOK)){
                String[] booksToDelete = request.getParameterValues(CHECKBOX_NAME_BOOK_ID);
                if(booksToDelete != null){
                    for(String id : booksToDelete){
                        bookService.deleteBook(id);
                    }
                }
                this.addToChangesMade(session);
                response.sendRedirect(response.encodeURL(BOOK_LIST_REQUEST));
                return;
            }else if(requestType.equalsIgnoreCase(RTYPE_ADD_BOOK)){
                destination = ADD_EDIT_BOOK_PAGE;
                
                List<Author> authors = authorService.findAll();
                request.setAttribute(AUTHORS, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_EDIT_BOOK)){
                destination = ADD_EDIT_BOOK_PAGE;
                
                String id = request.getParameter(BOOK_ID_TO_EDIT);
                Book book = bookService.find(id);
                request.setAttribute(INPUT_BOOK_ID, book.getBookId());
                request.setAttribute(INPUT_TITLE, book.getTitle());
                request.setAttribute(INPUT_ISBN, book.getIsbn());
                /*Used to set default author displayed in HTML select*/
                request.setAttribute(AUTHOR_ID, book.getAuthor().getAuthorId());
                
                List<Author> authors = authorService.findAll();
                request.setAttribute(AUTHORS, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_SAVE_BOOK)){
                String id = request.getParameter(INPUT_BOOK_ID);
                String title = request.getParameter(INPUT_TITLE); 
                String isbn = request.getParameter(INPUT_ISBN);
                String authorId = request.getParameter(SELECT_AUTHORS);
               
                if(title != null && !title.isEmpty() && isbn != null && !isbn.isEmpty() 
                        && authorId != null && !authorId.isEmpty()){
                    bookService.addOrUpdate(id, title, isbn, authorId);
                    this.addToChangesMade(session);
                    response.sendRedirect(response.encodeURL(BOOK_LIST_REQUEST));
                    return;
                }else{
                    if(id != null && !id.isEmpty()){
                        Book book = bookService.find(id);
                        request.setAttribute(INPUT_BOOK_ID, book.getBookId());
                        request.setAttribute(INPUT_TITLE, book.getTitle());
                        request.setAttribute(INPUT_ISBN, book.getIsbn());
                        request.setAttribute(AUTHOR_ID, book.getAuthor().getAuthorId());
                        
                        List<Author> authors = authorService.findAll();
                        request.setAttribute(AUTHORS, authors);
                    }
                    destination = ADD_EDIT_BOOK_PAGE;
                }
            }else if(requestType.equalsIgnoreCase(RTYPE_BOOK_LIST)){
                destination = BOOK_LIST_PAGE;
                List<Book> books = bookService.findAll();
                request.setAttribute(BOOKS, books);
            }else{
                request.setAttribute("errorMsg", ERROR_INVALID_PARAM);
            }
        }catch(Exception e){
            destination = ERROR_PAGE;
            request.setAttribute("errorMsg", e.getMessage());
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
}
