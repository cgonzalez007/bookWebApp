package edu.wctc.cbg.bookwebapp.controller;

import edu.wctc.cbg.bookwebapp.model.Author;
import edu.wctc.cbg.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chris Gonzalez
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/ac"})
public class AuthorController extends HttpServlet {
    public static final String HOME_PAGE = "/index.jsp";
    public static final String AUTHOR_LIST_PAGE = "/authorList.jsp";
    
    public static final String REQUEST_TYPE = "rType";
    public static final String RTYPE_AUTHOR_LIST = "authorList";
    public static final String RTYPE_HOME = "backHome";
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
            if(requestType.equalsIgnoreCase(RTYPE_AUTHOR_LIST)){
                try{
                    AuthorService authorService= new AuthorService();
                    destination = AUTHOR_LIST_PAGE;
                    List<Author> authors = 
                            authorService.retrieveAuthorInformation();
                    request.setAttribute("authors", authors);
                }catch (Exception e) {
                    destination = AUTHOR_LIST_PAGE;
                    request.setAttribute("errMsg", e.getMessage());
                }    
            }else if(requestType.equalsIgnoreCase(RTYPE_HOME)){
                 try{
                    destination = destination = HOME_PAGE;
                }catch (Exception e) {
                    destination = destination = HOME_PAGE;
                    request.setAttribute("errMsg", e.getMessage());
                }    
            }
        } catch (Exception e) {
            destination = HOME_PAGE;
            request.setAttribute("errMsg", e.getMessage());
        }
        RequestDispatcher view =
                request.getRequestDispatcher(destination);
        view.forward(request, response);
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
