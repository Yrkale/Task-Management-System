package com.taskmanagementsystem.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.taskmanagementsystem.model.Project;
import com.taskmanagementsystem.util.DBConnection;

public class ProjectDAO {

    public List<Project> getAllProjects() {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, u.name AS manager_name, p.start_date, p.end_date, p.progress_percentage, p.p_status " +
                "FROM projects p LEFT JOIN users u ON p.manager_id = u.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
               project.setManagerName(rs.getString("manager_name"));  // on project owner view we can see manager column because this line 

               project.setStartDate(rs.getDate("start_date"));
               project.setEndDate(rs.getDate("end_date"));
               project.setProgressPercentage(rs.getInt("progress_percentage"));
              

               project.setpStatus(rs.getString("p_status")); // <-- Added
                list.add(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean createProject(Project project) {
        boolean success = false;
        String sql = "INSERT INTO projects (name, description, manager_id, p_status, start_date, end_date, priority, progress_percentage, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setInt(3, project.getManagerId());
            ps.setString(4, project.getpStatus());
            ps.setDate(5, new java.sql.Date(project.getStartDate().getTime()));
            ps.setDate(6, new java.sql.Date(project.getEndDate().getTime()));
            ps.setString(7, project.getPriority());
            ps.setInt(8, project.getProgressPercentage());

            int rows = ps.executeUpdate();
            success = rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    public List<Project> getProjectsByManagerId(int managerId) {
        List<Project> projectList = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE manager_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setManagerId(rs.getInt("manager_id"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                project.setProgressPercentage(rs.getInt("progress_percentage"));
                
                project.setpStatus(rs.getString("p_status")); // <-- Added
                projectList.add(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }

    public boolean isProjectManagedBy(int projectId, int managerId) {
        String sql = "SELECT COUNT(*) FROM projects WHERE id = ? AND manager_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, projectId);
            ps.setInt(2, managerId);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Add this method in ProjectDAO.java
    public boolean updateProjectStatus(int projectId, String pStatus) {
        String sql = "UPDATE projects SET p_status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pStatus);
            ps.setInt(2, projectId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
