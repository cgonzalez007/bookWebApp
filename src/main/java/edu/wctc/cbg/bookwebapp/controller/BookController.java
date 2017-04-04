package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.model.AuthorFacade;
import edu.wctc.cbg.bookwebapp.model.BookFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    /*pages*/
    private static final String ERROR_PAGE = "/errorPage.jsp"; 
    private static final String HOME_PAGE = "index.jsp";
    
    /**
     * Attribute's name used to indicate request type
     */
    
    private static final String REQUEST_TYPE = "rType";
    
    /**
     * Request types:
     */
    private static final String RTYPE_HOME = "home";
    private static final String RTYPE_BOOK_LIST = "bookList";
    private static final String RTYPE_DELETE_BOOK = "deleteBook";
    private static final String RTYPE_ADD_BOOK = "addBook";
    private static final String RTYPE_EDIT_BOOK = "editBook";
    private static final String RTYPE_SAVE_BOOK = "saveBook";
    
    
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
            if(requestType.equalsIgnoreCase(RTYPE_DELETE_BOOK)){
               
            }else if(requestType.equalsIgnoreCase(RTYPE_SAVE_BOOK)){
            
            }else if(requestType.equalsIgnoreCase(RTYPE_BOOK_LIST)){
            
            }
        }catch(Exception e){
            request.setAttribute("errorMsg", ERROR_INVALID_PARAM);
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

}
