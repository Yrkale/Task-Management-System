package com.taskmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagementsystem.model.User;
import com.taskmanagementsystem.util.DBConnection;

public class UserDAO {
	
// ---------------------------------------------------------------------------------------------------

    public User authenticateUser(String email, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password")); // Optionally remove if not needed
                    user.setRole(rs.getString("role"));

                    int managerId = rs.getInt("manager_id");
                    if (!rs.wasNull()) {
                        user.setManagerId(managerId);
                    }

                    return user;
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
            throw e;
        }

        return null; // If authentication failed
    }
    
// ---------------------------------------------------------------------------------------------------    

    public boolean registerUser(User user) throws Exception {
        String insertSql = "INSERT INTO users (name, email, password, role, manager_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (isEmailExists(conn, user.getEmail())) {
                return false; // Email already exists
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getRole());
                ps.setInt(5, user.getManagerId());                
                 
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Database error in registerUser: " + e.getMessage());
            throw e;
        }
    }
    
    
// ---------------------------------------------------------------------------------------------------

    private boolean isEmailExists(Connection conn, String email) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
            throw e;
        }
    }
    

// ---------------------------------------------------------------------------------------------------    
    
// this featch all manager for project owner (but if we have multiple po then we should get manager     
    //  by their manager id column which is same as po .
    
    public List<User> getAllManagers() {
        List<User> managers = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users WHERE role = 'manager'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User manager = new User();
                manager.setId(rs.getInt("id"));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                managers.add(manager);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return managers;
    }
    
// ---------------------------------------------------------------------------------------------------    
    
// this method is not in use
    public List<User> getAllEmployees() {
        List<User> employees = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users WHERE role = 'employee'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User employee = new User();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
    
    
// ---------------------------------------------------------------------------------------------------    
    
    
   

    public List<User> getEmployeesByManagerId(int managerId) {
        List<User> employees = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'employee' AND manager_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User employee = new User();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setRole(rs.getString("role"));
                employee.setManagerId(rs.getInt("manager_id"));
                employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
    
    
// ---------------------------------------------------------------------------------------------------
    
    public boolean checkPassword(int id, String oldPassword) {
        String sql = "SELECT * FROM users WHERE id=? AND password=?";
        try (Connection conn = DBConnection.getConnection(); 
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, oldPassword);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // returns true if a match is found
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
// ---------------------------------------------------------------------------------------------------    

    public boolean updatePassword(int id, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); 
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
// ---------------------------------------------------------------------------------------------------


}

