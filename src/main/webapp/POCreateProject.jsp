<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.dao.UserDAO" %>
<%@ page import="com.taskmanagementsystem.model.User" %>

<%
    UserDAO userDAO = new UserDAO();
    List<User> managerList = userDAO.getAllManagers();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Project</title>
    <link rel="stylesheet" href="css/POCreateProject.css">
</head>
<body>

<h2>Create New Project</h2>

<form action="CreateProjectServlet" method="post">
    <label>Project Name:</label>
    <input type="text" name="name" required><br><br>

    <label>Description:</label>
    <textarea name="description" rows="4" required></textarea><br><br>

    <label>Start Date:</label>
    <input type="date" name="start_date" required><br><br>

    <label>End Date:</label>
    <input type="date" name="end_date" required><br><br>

    <label>Priority:</label>
    <select name="priority" required>
        <option value="Low">Low</option>
        <option value="Medium" selected>Medium</option>
        <option value="High">High</option>
    </select><br><br>

    <label>Progress Percentage:</label>
    <input type="number" name="progress_percentage" value="0" min="0" max="100" required><br><br>
    
    <label for="managerId">Assign Manager:</label>
            <select name="managerId" id="managerId" required>
                <option value="" disabled selected>-- Select Manager --</option>
                <% for (User manager : managerList) { %>
                    <option value="<%= manager.getId() %>"><%= manager.getName() %> (<%= manager.getEmail() %>)</option>
                <% } %>
            </select><br><br>
    
    

    <input type="submit" value="Create Project">
     <a href="Dashboard.jsp" class="btn">Back to Dashboard</a>
         <div style="clear: both;"></div>
</form>


</body>
</html>
