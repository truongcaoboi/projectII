/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import dataAccess.DataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Company;
import model.Job;
import org.bson.Document;

/**
 *
 * @author Xuan Truong PC
 */
public class GetDataFirst extends HttpServlet {

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
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

            Vector<Job> jobs = new Vector<Job>();
            Vector<Company> companys = new Vector<Company>();
            Vector<Job> jobHighSalary = new Vector<Job>();
            Vector<Job> jobExpired = new Vector<Job>();
            Vector<Job> jobSearch = new Vector<Job>();

            if ((Vector<Job>) request.getSession().getAttribute("jobs") == null) {
                DataAccess da = new DataAccess();
                MongoCollection<Document> mongoColection = da.getConnection();
                for (Document doc : mongoColection.find()) {
                    Company company = new Company();
                    Job job = new Job();
                    job.setAddress(doc.getString("companyAddress"));
                    job.setCareer(doc.getString("career"));
                    job.setCompanyLink(doc.getString("companyLink"));
                    job.setCompanyName(doc.getString("companyName"));
                    job.setTitleJob(doc.getString("titleJob"));
                    job.setSalary(doc.getString("salary"));
                    job.setExpire(output.format(doc.getDate("expire")));
                    job.setExperience(doc.getString("experience"));
                    job.setArea(doc.getString("area"));
                    job.setId(doc.get("_id").toString());
                    job.setLogo(doc.getString("logo"));
                    job.setCareer(doc.getString("career"));
                    if (new Date().getTime() <= doc.getDate("expire").getTime()) {
                        jobs.add(job);
                        jobSearch.add(job);
                        if (doc.getDouble("minSalary") >= 10000000) {
                            jobHighSalary.add(job);
                        }

                        if ((doc.getDate("expire").getTime() - new Date().getTime()) <= 2 * 24 * 3600 * 1000) {
                            jobExpired.add(job);
                        }
                    }

                    if (!doc.getString("companyLink").equals("#") & !(doc.getString("logo") == null) & !Company.isDuplicate(doc.getString("companyName"), companys)) {
                        company.setLink(doc.getString("companyLink"));
                        company.setLogo(doc.getString("logo"));
                        company.setName(doc.getString("companyName"));
                        companys.add(company);
                    }
                }
                da.closeConnect();
            } else {
                jobs = (Vector<Job>) request.getSession().getAttribute("jobs");
                companys = (Vector<Company>) request.getSession().getAttribute("companys");
                jobHighSalary = (Vector<Job>) request.getSession().getAttribute("jobHighSalary");
                jobExpired = (Vector<Job>) request.getSession().getAttribute("jobExpire");
                jobSearch = jobs;
            }

            String responseJobs = "[";
            String responseCompanys = "[";
            String responseJobHighSalary = "[";
            String responseJobExpire = "[";
            for (int i = 0; i < 12; i++) {
                responseCompanys += companys.get(i).toJson() + ",";
            }
            responseCompanys = responseCompanys.substring(0, responseCompanys.length() - 1);
            responseCompanys += "]";

            if (jobSearch.size() == 0) {
                responseJobs = "[]";
            } else {
                for (int i = 0; i < 20; i++) {
                    responseJobs += jobs.get(i).toJsonImportant() + ",";
                }
                responseJobs = responseJobs.substring(0, responseJobs.length() - 1);
                responseJobs += "]";
            }

            if (jobHighSalary.size() == 0) {
                responseJobHighSalary = "[]";
            } else {
                for (int i = 0; i < 10; i++) {
                    responseJobHighSalary += jobHighSalary.get(i).toJsonImportant() + ",";
                }
                responseJobHighSalary = responseJobHighSalary.substring(0, responseJobHighSalary.length() - 1);
                responseJobHighSalary += "]";
            }

            if (jobExpired.size() == 0) {
                responseJobExpire = "[]";
            } else {
                for (int i = 0; i < 5; i++) {
                    responseJobExpire += jobExpired.get(i).toJsonImportant() + ",";
                }
                responseJobExpire = responseJobExpire.substring(0, responseJobExpire.length() - 1);
                responseJobExpire += "]";
            }
            //luu lai
            request.getSession().setAttribute("jobs", jobs);
            request.getSession().setAttribute("companys", companys);
            request.getSession().setAttribute("jobHighSalary", jobHighSalary);
            request.getSession().setAttribute("jobExpire", jobExpired);
            request.getSession().setAttribute("jobSearch", jobSearch);

            //out.print(jobs.size()+" "+jobHighSalary.size()+" "+jobSearch.size());
            out.print("{\"status\":\"OK\",\"companys\":" + responseCompanys + ",\"jobs\":" + responseJobs + ",\"jobHighSalary\":" + responseJobHighSalary + ",\"jobExpire\":" + responseJobExpire + "}");
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
