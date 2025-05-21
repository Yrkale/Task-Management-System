package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dao.ProjectDAO;
import com.taskmanagementsystem.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/CreateProjectServlet")
public class CreateProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProjectDAO projectDAO;

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO();  
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the form
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String managerIdStr = request.getParameter("managerId");
        String status="ongoing";
        
        String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");
        String priority = request.getParameter("priority");
        String progressPercentageStr = request.getParameter("progress_percentage");
       
        

        try {
            int managerId = Integer.parseInt(managerIdStr);
            int progressPercentage = Integer.parseInt(progressPercentageStr);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            

            // Create a Project object
            Project project = new Project();
            project.setName(name);
            project.setDescription(description);
            project.setManagerId(managerId);
            project.setpStatus(status);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setPriority(priority);
            project.setProgressPercentage(progressPercentage);
             

            // Insert into DB
            boolean success = projectDAO.createProject(project);

            if (success) {
                response.sendRedirect("Dashboard.jsp"); // Redirect to dashboard after success
            } else {
                request.setAttribute("error", "Project creation failed.");
                request.getRequestDispatcher("POCreateProject.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("POCreateProject.jsp").forward(request, response);
        }
    }
}
