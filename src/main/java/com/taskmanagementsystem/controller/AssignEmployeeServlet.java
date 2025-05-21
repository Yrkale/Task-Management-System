package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dao.TaskDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AssignEmployeeServlet")
public class AssignEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            int employeeId = Integer.parseInt(request.getParameter("employeeId")); //selected employee ID

            // Update task with selected employee
            taskDAO.assignTaskToEmployee(taskId, employeeId);

            // Redirect back with success
            response.sendRedirect("MAssignTasks.jsp?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MAssignTasks.jsp?error=true");
        }
    }
}
