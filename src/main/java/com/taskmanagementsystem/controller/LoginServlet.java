package com.taskmanagementsystem.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.taskmanagementsystem.dao.UserDAO;
import com.taskmanagementsystem.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();

        try {
            User user = userDao.authenticateUser(email, password);

            HttpSession session = request.getSession();

            if (user != null) {
                // Store the full User object in session
                session.setAttribute("loggedInUser", user);

                // Redirect to a common dashboard.jsp
                response.sendRedirect("Dashboard.jsp");
            } else {
                session.setAttribute("loginError", "Invalid email or password.");
                response.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("loginError", "An error occurred during login.");
            response.sendRedirect("login.jsp");
        }
    }
}
