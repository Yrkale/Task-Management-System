<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.model.Task" %>
<%@ page import="com.taskmanagementsystem.dao.TaskDAO" %>
<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    TaskDAO taskDAO = new TaskDAO();
    List<Task> tasks = taskDAO.getTasksByManagerId(user.getId()); 
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Your Tasks</title>
    <link rel="stylesheet" type="text/css" href="css/MViewTasks.css">
</head>
<body>
   <div class="table-wrapper">
        <h1>Your Created Tasks</h1>
         <a href="Dashboard.jsp" class="btn">Back to Dashboard</a><br><br>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Task Name</th>
                    <th>Description</th>
                    <th>Priority</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Project ID</th>
                    <th>Manager ID</th>
                    <th>Employee ID</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Remark</th>
                    <th>Completion Date</th>
                    <th>Progress (%)</th>
                </tr>
            </thead>
            <tbody>
                <% for (Task task : tasks) { %>
                    <tr>
                        <td><%= task.getId() %></td>
                        <td><%= task.getTaskName() %></td>
                        <td><%= task.getDescription() %></td>
                        <td><%= task.getPriority() %></td>
                        <td><%= task.getDueDate() %></td>
                        <td><%= task.gettStatus() %></td>
                        <td><%= task.getProjectId() %></td>
                        <td><%= task.getManagerId() %></td>
                        <td><%= task.getEmployeeId() %></td>
                        <td><%= task.getCreatedAt() %></td>
                        <td><%= task.getUpdatedAt() %></td>
                        <td><%= task.getRemark() %></td>
                        <td><%= task.getCompletionDate() %></td>
                        <td><%= task.getProgressPercentage() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
       
    </div>
</body>
</html>
