package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.entity.Author;
import edu.wctc.cbg.bookwebapp.entity.Book;
import edu.wctc.cbg.bookwebapp.service.AuthorService;
import edu.wctc.cbg.bookwebapp.service.BookService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
 * @author cgonz
 */
@WebServlet(name = "BookController", urlPatterns = {"/bc"})
public class BookController extends HttpServlet {
    
    private AuthorService authorService;
    private BookService bookService;
    
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
    private static final String RTYPE_REFRESH_AJAX = "refreshAjax";
    
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
                PrintWriter out = response.getWriter();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = request.getReader();
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } finally {
                        br.close();
                    }                 
                String payload = sb.toString();
                JsonReader reader = Json.createReader(new StringReader(payload));
                JsonObject jsonObj = reader.readObject();
                JsonArray jsonArray = jsonObj.getJsonArray("bookIds");
                List<String> booksToDelete = new ArrayList<>();
                
                if(jsonArray != null && !jsonArray.isEmpty()){
                    for(int i = 0 ; i < jsonArray.size() ; i++){
                        booksToDelete.add(jsonArray.getString(i));
                    }
                }
                
                if(!booksToDelete.isEmpty()){
                    for(String id : booksToDelete){
                        bookService.removeById(id);
                    }
                    this.addToChangesMade(session);
                }
                
                response.setContentType("application/json; charset=UTF-8");
                response.setStatus(200);
                out.write("{\"success\":\"true\"}");
                out.flush();
                    
                return;
            }else if(requestType.equalsIgnoreCase(RTYPE_ADD_BOOK)){
                destination = ADD_EDIT_BOOK_PAGE;
                
                List<Author> authors = authorService.findAllAlphabetically();
                request.setAttribute(AUTHORS, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_EDIT_BOOK)){
                destination = ADD_EDIT_BOOK_PAGE;
                
                String id = request.getParameter(BOOK_ID_TO_EDIT);
                Book book = bookService.findById(id);
                request.setAttribute(INPUT_BOOK_ID, book.getBookId());
                request.setAttribute(INPUT_TITLE, book.getTitle());
                request.setAttribute(INPUT_ISBN, book.getIsbn());
                /*Used to set default author displayed in HTML select*/
                request.setAttribute(AUTHOR_ID, book.getAuthor().getAuthorId());
                
                List<Author> authors = authorService.findAllAlphabetically();
                request.setAttribute(AUTHORS, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_SAVE_BOOK)){
                String id = request.getParameter(INPUT_BOOK_ID);
                String title = request.getParameter(INPUT_TITLE); 
                String isbn = request.getParameter(INPUT_ISBN);
                String authorId = request.getParameter(SELECT_AUTHORS);
               
                if(title != null && !title.isEmpty() && isbn != null && !isbn.isEmpty() 
                        && authorId != null && !authorId.isEmpty()){
                    bookService.saveOrEdit(id, title, isbn, authorId);
                    this.addToChangesMade(session);
                    response.sendRedirect(response.encodeURL(BOOK_LIST_REQUEST));
                    return;
                }else{
                    if(id != null && !id.isEmpty()){
                        Book book = bookService.findById(id);
                        request.setAttribute(INPUT_BOOK_ID, book.getBookId());
                        request.setAttribute(INPUT_TITLE, book.getTitle());
                        request.setAttribute(INPUT_ISBN, book.getIsbn());
                        request.setAttribute(AUTHOR_ID, book.getAuthor().getAuthorId());
                        
                        List<Author> authors = authorService.findAllAlphabetically();
                        request.setAttribute(AUTHORS, authors);
                    }
                    destination = ADD_EDIT_BOOK_PAGE;
                }
            }else if(requestType.equalsIgnoreCase(RTYPE_BOOK_LIST)){
                destination = BOOK_LIST_PAGE;
                List<Book> books = bookService.findAll();
                request.setAttribute(BOOKS, books);
            }else if(requestType.equalsIgnoreCase(RTYPE_REFRESH_AJAX)){
                this.refreshListAJAX(request, response);
                return;
            }else{
                request.setAttribute("errorMsg", ERROR_INVALID_PARAM);
            }
        }catch(Exception e){
            destination = ERROR_PAGE;
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
    
    private void refreshListAJAX(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.findAll();       
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        
        for(Book book : books){
            jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                    .add("bookId", book.getBookId())
                    .add("title", book.getTitle())
                    .add("isbn", book.getIsbn())
                    .add("authorId", book.getAuthor().getAuthorId())
                    .add("authorName", book.getAuthor().getAuthorName())
            );
        }
        
        JsonArray booksJson = jsonArrayBuilder.build();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(booksJson.toString());
        out.flush();
    }
}
