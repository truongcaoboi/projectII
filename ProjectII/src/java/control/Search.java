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
public class Search extends HttpServlet {

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
            Vector<Job> jobs = (Vector<Job>)request.getSession().getAttribute("jobs");
            String title = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8");
            String career = java.net.URLDecoder.decode(request.getParameter("career"), "UTF-8");
            String district = java.net.URLDecoder.decode(request.getParameter("district"), "UTF-8");
            
            Vector<Job> jobSearch = new Vector<Job>();
            for(Job job:jobs){
                if(job.getTitleJob().contains(title.toUpperCase())&job.getCareer().contains(career)&job.getAddress().contains(district)){
                    jobSearch.add(job);
                }
            }
            
            String responseJob = "[";
            if(jobSearch.size()==0){
                responseJob = "[]";
            }else{
                int n = 0;
                if(jobSearch.size()>=20){
                    n=20;
                }else{
                    n = jobSearch.size();
                }
                for(int i =0;i<n;i++){
                    responseJob += jobSearch.get(i).toJsonImportant()+",";
                }
                responseJob = responseJob.substring(0, responseJob.length()-1);
                responseJob +="]";
            }
            out.print("{\"jobs\":"+responseJob+"}");
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
