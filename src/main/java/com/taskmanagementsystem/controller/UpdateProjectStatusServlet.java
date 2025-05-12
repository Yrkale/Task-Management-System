package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dao.ProjectDAO;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdateProjectStatusServlet")
public class UpdateProjectStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String pStatus = request.getParameter("pStatus");
        int progress = Integer.parseInt(request.getParameter("progress"));
        
        

        ProjectDAO projectDAO = new ProjectDAO();
        boolean success = projectDAO.updateProjectStatus(projectId, pStatus, progress);

        if (success) {
            // Redirect back to dashboard or project list
            response.sendRedirect("Dashboard.jsp");  // Or wherever your project list is shown
        } else {
            response.getWriter().println("Failed to update project status.");
        }
    }
}
