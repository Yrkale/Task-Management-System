package com.taskmanagementsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.util.DBConnection;

public class TaskDAO {
	
	
// ---------------------------------------------------------------------------------------------------

    public boolean createTasks(List<Task> tasks) throws Exception {
        String sql = "INSERT INTO tasks (task_name, description, priority, due_date, tStatus, project_id, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Task task : tasks) {
                ps.setString(1, task.getTaskName());
                ps.setString(2, task.getDescription());
                ps.setString(3, task.getPriority());
                ps.setDate(4, task.getDueDate());

                String status = task.gettStatus();
                if (status == null || status.trim().isEmpty()) {
                    status = "ongoing";
                }
                ps.setString(5, status);
                ps.setInt(6, task.getProjectId());
                ps.setInt(7, task.getManagerId());

                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            for (int result : results) {
                if (result <= 0) {
                    return false;
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error creating tasks: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
// ---------------------------------------------------------------------------------------------------

   
    public List<Integer> getAllEmployeeIds() {
        List<Integer> employeeIds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE role = 'employee'")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employeeIds.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeIds;
    }
    
    
// ---------------------------------------------------------------------------------------------------   

    public boolean assignTaskToEmployee(int taskId, int employeeId) {
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE tasks SET employee_id = ?, tStatus = 'ongoing', updated_at = NOW() WHERE id = ?")) {

            ps.setInt(1, employeeId);
            ps.setInt(2, taskId);

            updated = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }
    
// ---------------------------------------------------------------------------------------------------    

   
    public List<Task> getTasksByManagerId(int managerId) {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE manager_id = ?")) {

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTaskName(rs.getString("task_name"));
                task.setDescription(rs.getString("description"));
                task.setPriority(rs.getString("priority"));
                task.setDueDate(rs.getDate("due_date"));
                task.settStatus(rs.getString("tStatus"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setProjectId(rs.getInt("project_id"));
                task.setManagerId(rs.getInt("manager_id"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                task.setRemark(rs.getString("remark"));
                task.setCompletionDate(rs.getDate("completion_date"));
                task.setProgressPercentage(rs.getInt("progress_percentage"));
                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
    
    
// ---------------------------------------------------------------------------------------------------    

    public List<Task> getTasksByEmployeeId(int id) {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE employee_id = ?")) {
        	
        	// ongoing task only code it leter

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTaskName(rs.getString("task_name"));
                task.setDescription(rs.getString("description"));
                task.setPriority(rs.getString("priority"));
                task.setDueDate(rs.getDate("due_date"));
                task.settStatus(rs.getString("tStatus"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setProjectId(rs.getInt("project_id"));
                task.setManagerId(rs.getInt("manager_id"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                task.setRemark(rs.getString("remark"));
                task.setCompletionDate(rs.getDate("completion_date"));
                task.setProgressPercentage(rs.getInt("progress_percentage"));
                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
    
    
// ---------------------------------------------------------------------------------------------------    
    
    
    public boolean updateTaskByEmployee(Task task) {
        boolean rowUpdated = false;
        if(task.getProgressPercentage()==100)
        {        	 
                 task.setCompletionDate(Date.valueOf(LocalDate.now()));
             
        	
        	 String sql = "UPDATE tasks SET remark = ?, progress_percentage = ?, tStatus = ?, completion_date=? , updated_at = CURRENT_TIMESTAMP WHERE id = ?";

             try (Connection conn = DBConnection.getConnection();
                  PreparedStatement stmt = conn.prepareStatement(sql)) {

                 stmt.setString(1, task.getRemark());
                 stmt.setInt(2, task.getProgressPercentage());
                 stmt.setString(3, "complete"); // when progress % is 100 means implicitly project completed
                 stmt.setDate(4, task.getCompletionDate());
                 stmt.setInt(5, task.getId());
                

                 rowUpdated = stmt.executeUpdate() > 0;
             } catch (Exception e) {
                 e.printStackTrace();
             }   
        	
        }
        
        else {
        String sql = "UPDATE tasks SET remark = ?, progress_percentage = ?, tStatus = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getRemark());
            stmt.setInt(2, task.getProgressPercentage());
            stmt.setString(3, "on going");
            stmt.setInt(4, task.getId());

            rowUpdated = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        }
		return rowUpdated;
    }
    
    
// ---------------------------------------------------------------------------------------------------
    
    
    public List<Task> getAllRunningTasksUnderManager(int employeeId, String status) {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Get the manager ID of the current employee
            String getManagerIdQuery = "SELECT manager_id FROM users WHERE id = ?";
            int managerId = -1;

            try (PreparedStatement stmt = conn.prepareStatement(getManagerIdQuery)) {
                stmt.setInt(1, employeeId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    managerId = rs.getInt("manager_id");
                }
            }

            // Step 2: If manager exists, get all tasks created by this manager
            if (managerId != -1) {
                String query = "SELECT * FROM tasks WHERE manager_id = ? AND tStatus = ?";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, managerId);
                    ps.setString(2, status);

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Task task = new Task();
                        task.setId(rs.getInt("id"));
                        task.setTaskName(rs.getString("task_name"));
                        task.setDescription(rs.getString("description"));
                        task.setEmployeeId(rs.getInt("employee_id"));
                        task.settStatus(status);   //setStatus(rs.getString("status"));
                        tasks.add(task);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    
    
// ---------------------------------------------------------------------------------------------------
    
    

    
}
