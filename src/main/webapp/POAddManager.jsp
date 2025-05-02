<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page import="com.taskmanagementsystem.dao.UserDAO" %>

<%@ page session="true" %>

<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"PROJECT OWNER".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    int managerId = user.getId();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Manager</title>
    <link rel="stylesheet" href="css/add_manager.css"> <!-- Reuse same CSS for consistency -->
</head>
<body>
    <div class="form-container">
        <h2>Add New Manager</h2>

        <form action="addManagerServlet" method="post">
            <label for="name">Full Name:</label>
            <input type="text" name="name" id="name" required>

            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>

            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>

            <input type="hidden" name="role" value="MANAGER">
            
            <input type="hidden" name="manager_id" value="<%= managerId %>">

            <button type="submit">Add Manager</button>
        </form>

        <a href="Dashboard.jsp">← Back to Dashboard</a>
    </div>
</body>
</html>
