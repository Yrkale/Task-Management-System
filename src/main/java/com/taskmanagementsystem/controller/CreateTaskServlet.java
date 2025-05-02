package com.taskmanagementsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagementsystem.dao.ProjectDAO;
import com.taskmanagementsystem.dao.TaskDAO;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreateTaskServlet")
public class CreateTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User manager = (User) session.getAttribute("loggedInUser");

        if (manager == null || !"MANAGER".equalsIgnoreCase(manager.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int projectId = Integer.parseInt(request.getParameter("projectId"));
            String[] taskNames = request.getParameterValues("taskName");
            String[] descriptions = request.getParameterValues("description");
            String[] priorities = request.getParameterValues("priority");
            String[] dueDates = request.getParameterValues("dueDate");

            ProjectDAO projectDAO = new ProjectDAO();
            if (!projectDAO.isProjectManagedBy(projectId, manager.getId())) {
                request.setAttribute("error", "You can only create tasks for your assigned projects.");
                request.getRequestDispatcher("MCreateTasks.jsp").forward(request, response);
                return;
            }

            List<Task> tasks = new ArrayList<>();
            for (int i = 0; i < taskNames.length; i++) {
                Task task = new Task();
                task.setProjectId(projectId);
                task.setTaskName(taskNames[i]);
                task.setDescription(descriptions[i]);
                task.setPriority(priorities[i]);
                task.setDueDate(java.sql.Date.valueOf(dueDates[i]));
                task.settStatus("Not Started"); // Default status
                task.setManagerId(manager.getId()); // ✅ Important: Set Manager ID
                tasks.add(task);
            }

            boolean success = taskDAO.createTasks(tasks);

            if (success) {
                response.sendRedirect("Dashboard.jsp?success=Tasks created successfully");
            } else {
                request.setAttribute("error", "Failed to create some tasks.");
                request.getRequestDispatcher("MCreateTasksjsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid project ID.");
            request.getRequestDispatcher("MCreateTasks.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("MCreateTasks.jsp").forward(request, response);
        }
    }
}
