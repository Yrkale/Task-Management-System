package com.taskmanagementsystem.controller;

import java.io.IOException;

import com.taskmanagementsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // Only update if user checked the "Change Password" option
        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            UserDAO dao = new UserDAO();
            boolean valid = dao.checkPassword(id, oldPassword);
            
            if (valid) {
                dao.updatePassword(id, newPassword);
                request.setAttribute("message", "Password updated successfully.");
            } else {
                request.setAttribute("error", "Old password is incorrect.");
            }
        }

        // Forward back to the dashboard or show message
        request.getRequestDispatcher("Dashboard.jsp").forward(request, response);
    }
}
