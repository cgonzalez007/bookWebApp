package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.model.Author;
import edu.wctc.cbg.bookwebapp.model.AuthorDao;
import edu.wctc.cbg.bookwebapp.model.AuthorService;
import edu.wctc.cbg.bookwebapp.model.MySqlDbAccessor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chris Gonzalez 2017
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/ac"})
public class AuthorController extends HttpServlet {
    public static final String ERROR_INVALID_PARAM = "ERROR: Invalid Parameter";
    
    public static final String HOME_PAGE = "/index.jsp";
    public static final String AUTHOR_LIST_PAGE = "/authorList.jsp";
    public static final String ADD_EDIT_AUTHOR_PAGE = "/addEditAuthor.jsp";
    
    public static final String AUTHOR_LIST_ATTRIBUTE = "authors";
    
    public static final String REQUEST_TYPE = "rType";
    public static final String RTYPE_AUTHOR_LIST = "authorList";
    public static final String RTYPE_HOME = "home";
    public static final String RTYPE_DELETE_AUTHOR = "deleteAuthor";
    public static final String RTYPE_ADD_AUTHOR = "addAuthor";
    public static final String RTYPE_SAVE_AUTHOR = "saveAuthor";
    
    public static final String INPUT_NAME_AUTHOR_CHECKBOX = "authorId";
    
    public static final String AUTHOR_TABLE_NAME = "author";
    public static final String AUTHOR_ID_COL_NAME = "author_id";
    public static final int MAX_RECORDS = 50;
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
            AuthorService authorService = new AuthorService(
                new AuthorDao(
                        new MySqlDbAccessor(),"com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin")
                        );
            if(requestType.equalsIgnoreCase(RTYPE_AUTHOR_LIST)){
                destination = AUTHOR_LIST_PAGE;
                List<Author> authors = authorService.retrieveAuthors(
                        AUTHOR_TABLE_NAME, MAX_RECORDS);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authors);
            }else if(requestType.equalsIgnoreCase(RTYPE_HOME)){
                destination = destination = HOME_PAGE;
            }else if(requestType.equalsIgnoreCase(RTYPE_DELETE_AUTHOR)){
                destination = AUTHOR_LIST_PAGE;
                String[] authorsToDelete = request.getParameterValues(INPUT_NAME_AUTHOR_CHECKBOX);
                if(authorsToDelete != null){
                    for(String id : authorsToDelete){
                        authorService.deleteAuthorById(AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, id);
                    }
                }
                refreshResults(request, authorService);
            }else if(requestType.equalsIgnoreCase(RTYPE_ADD_AUTHOR)){
                destination = ADD_EDIT_AUTHOR_PAGE;
                
                
                // NOT DONE
                
                
                
                
                
                refreshResults(request, authorService);
            }else{
                request.setAttribute("errMsg", ERROR_INVALID_PARAM);
            }
        } catch (Exception e) {
            destination = HOME_PAGE;
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
        RequestDispatcher view =
                request.getRequestDispatcher(destination);
        view.forward(request, response);
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

}
