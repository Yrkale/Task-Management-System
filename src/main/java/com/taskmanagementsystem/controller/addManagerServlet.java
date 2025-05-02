package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dao.UserDAO;
import com.taskmanagementsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/addManagerServlet")
public class addManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); 
        int managerId = Integer.parseInt(request.getParameter("manager_id"));

        User manager = new User(name, email, password, role);
        manager.setManagerId(managerId);

        try {
            boolean success = userDAO.registerUser(manager);

            if (success) {
                response.sendRedirect("Dashboard.jsp");
            } else {
                request.setAttribute("error", "Failed to add manager.");
                request.getRequestDispatcher("POAddManager.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Internal server error while adding manager.");
            request.getRequestDispatcher("POAddManager.jsp").forward(request, response);
        }
    }
}
