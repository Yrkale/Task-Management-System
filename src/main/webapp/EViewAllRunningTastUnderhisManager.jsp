<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.dao.TaskDAO" %>
<%@ page import="com.taskmanagementsystem.model.Task" %>
<%@ page import="jakarta.servlet.http.*,jakarta.servlet.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int employeeId = Integer.parseInt(request.getParameter("employeeId"));
    String status = "ongoing"; //  show only obgoing tasks

    TaskDAO taskDAO = new TaskDAO();
    List<Task> tasks = taskDAO.getAllRunningTasksUnderManager(employeeId, status);
%>

<html>
<head>
    <title>Running Tasks Assigned by Manager</title>
    <style>
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #3498db;
            color: white;
        }
        
        .btn {
    display: inline-block;
    padding: 10px 20px;
    background-color: #4CAF50; /* Green background ahe */
    color: white;
    text-decoration: none;
    font-weight: bold;
    border-radius: 5px;
    transition: background-color 0.3s ease;
    margin: 10px 0;
}

.btn:hover {
    background-color: #45a049; /* Darker green on hover */
}
    
    .dashboard-container {
    max-width: 1100px;
    margin: 30px auto;
    padding: 20px 30px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 8px 18px rgba(0, 0, 0, 0.1);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}    
        
        
    </style>
    
    
    
</head>
<body>
    <h2 style="text-align:center">Running Tasks Created by Your Manager</h2>
    
    <div class="dashboard-container">
    
     <a href="Dashboard.jsp" class="btn">Back to Dashboard</a><br><br>
    
    <table>
        <tr>
            <th>Task ID</th>
            <th>Task Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Assigned Employee</th>
        </tr>
        <%
            for(Task task : tasks) {
        %>
        <tr>
            <td><%= task.getId() %></td>
            <td><%= task.getTaskName() %></td>
            <td><%= task.getDescription() %></td>
            <td><%= task.gettStatus() %></td>
            <td><%= task.getEmployeeId() %></td>
        </tr>
        <%
            }
        %>
    </table>
    
    </div>
</body>
</html>
