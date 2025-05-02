package com.taskmanagementsystem.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.taskmanagementsystem.dao.UserDAO;
import com.taskmanagementsystem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role=request.getParameter("role");

        User user = new User(name, email, password, role);
        UserDAO userDao = new UserDAO();

        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter()) {
            boolean registrationSuccessful = userDao.registerUser(user);

            if (registrationSuccessful) {
                out.println("<html><body>");
                out.println("<h3>✅ Registration Successful!</h3>");
                out.println("<p>Welcome, " + name + "!</p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h3 style='color:red;'>❌ Registration Failed. Email already exists.</h3>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body>");
                out.println("<h3 style='color:red;'>❌ An unexpected error occurred during registration.</h3>");
                out.println("<p>Error: " + e.getMessage() + "</p>");
                out.println("</body></html>");
            }
        }
    }
}
