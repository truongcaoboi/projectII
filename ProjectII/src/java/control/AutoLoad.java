/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Job;

/**
 *
 * @author Xuan Truong PC
 */
public class AutoLoad extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String type = request.getParameter("typeLoad");
            int amount = Integer.parseInt(request.getParameter("amount"));
            //out.print("amount");
            Vector<Job> jobs = new Vector<Job>();
            if(type.equals("high")){
                jobs = (Vector<Job>)request.getSession().getAttribute("jobHighSalary");
            }else if(type.equals("expire")){
                jobs = (Vector<Job>)request.getSession().getAttribute("jobExpire");
            }else{
                jobs = (Vector<Job>)request.getSession().getAttribute("jobSearch");
            }
            String result = "[";
            int n=0;
            
            if(amount==jobs.size()){
                result = "[]";
                out.print("{\"status\":\"yes\",\"jobs\":"+result+"}");
            }
            
            if(amount+20<jobs.size()){
                n=amount+20;
            }else {
                n=jobs.size();
            }
            
            for(int i=amount;i<n;i++){
                result += jobs.get(i).toJsonImportant()+",";
            }
            result = result.substring(0,result.length()-1);
            result += "]";
            String status = "no";
            if(n==jobs.size()){
                status = "yes";
            }
            out.print("{\"amount\":"+amount+",\"status\":\""+status+"\",\"jobs\":"+result+"}");
            //out.print(amount);
        }
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
