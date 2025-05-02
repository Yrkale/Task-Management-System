<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.model.Task" %>
<%@ page import="com.taskmanagementsystem.dao.TaskDAO" %>
<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    TaskDAO taskDAO = new TaskDAO();
    int id = user.getId();
    List<Task> emptasks = taskDAO.getTasksByEmployeeId(id);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Your Tasks</title>
    <link rel="stylesheet" type="text/css" href="css/EViewTasks.css">
    <style>

.assign-btn {
    background-color: #2ecc71;
    color: white;
    border: none;
    padding: 7px 14px;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 8px;
    font-size: 13px;
    transition: background-color 0.3s ease;
}

.assign-btn:hover {
    background-color: #27ae60;
}

 

</style>
</head>
<body>


    <div class="container">
        <h1>Task Assigned to You</h1>
        
         <a href="Dashboard.jsp" class="btn">Back to Dashboard</a><br><br>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Task Name</th>
                    <th>Description</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Priority</th>
                    <th>Due Date</th>
                    <th>Remark</th>
                    <th>Progress</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (Task task : emptasks) { %>
                    <tr>
                        <form action="updateTaskStatus" method="post">
                            <td><%= task.getId() %>
                                <input type="hidden" name="taskId" value="<%= task.getId() %>">
                            </td>
                            <td><%= task.getTaskName() %></td>
                            <td><%= task.getDescription() %></td>
                            <td><%= task.getCreatedAt() %></td>
                            <td><%= task.getUpdatedAt() %></td>
                            <td><%= task.getPriority() %></td>
                            <td><%= task.getDueDate() %></td>

                            <td>
                                <input type="text" name="remark" value="<%= task.getRemark() != null ? task.getRemark() : "" %>" />
                            </td>
                            <td>
                                <input type="number" name="progress" min="0" max="100" value="<%= task.getProgressPercentage() %>" />%
                            </td>
                            <td>
                                <select name="tstatus" required>
                                    <option value="ongoing" <%= "ongoing".equals(task.gettStatus()) ? "selected" : "" %>>Ongoing</option>
                                    <option value="complete" <%= "complete".equals(task.gettStatus()) ? "selected" : "" %>>Complete</option>
                                </select>
                            </td>
                             
                            <td>
                                <button class="assign-btn" type="submit">Update</button>
                            </td>
                        </form>
                    </tr>
                <% } %>
            </tbody>
        </table>
       
    </div>
</body>
</html>
