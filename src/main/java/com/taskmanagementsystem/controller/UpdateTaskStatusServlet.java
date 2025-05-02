package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dao.TaskDAO;
import com.taskmanagementsystem.model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateTaskStatus")
public class UpdateTaskStatusServlet extends HttpServlet {
	
	private static final long serialVersionUID=1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String remark = request.getParameter("remark");
        String tstatus = request.getParameter("tstatus");
        int progress = Integer.parseInt(request.getParameter("progress"));
        
        Task task = new Task();
        task.setId(taskId);
        task.setRemark(remark);
        task.settStatus(tstatus);  
        task.setProgressPercentage(progress);
        
        
        

        TaskDAO taskDAO = new TaskDAO();
        

        boolean updated = taskDAO.updateTaskByEmployee(task);
        
        

        if (updated) {
            response.sendRedirect("EViewTasks.jsp");
        } else {
            response.getWriter().println("Task update failed.");
        }
    }
}
