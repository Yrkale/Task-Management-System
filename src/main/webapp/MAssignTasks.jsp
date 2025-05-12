<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.model.Task" %>
<%@ page import="com.taskmanagementsystem.dao.TaskDAO" %>
<%@ page import="com.taskmanagementsystem.dao.UserDAO" %>   <%-- (added to get employee list) --%>
<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String successMessage = request.getParameter("success");

    TaskDAO taskDAO = new TaskDAO();
    List<Task> tasks = taskDAO.getTasksByManagerId(user.getId());

    // Fetch employee list (only employees)
    UserDAO userDAO = new UserDAO();
    List<User> employees = userDAO.getEmployeesByManagerId(user.getId());

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assign Tasks</title>
    <link rel="stylesheet" type="text/css" href="css/MAssignTasks.css">
</head>
<body>
    <div class="container">
        <% if (successMessage != null) { %>
            <div class="toast">✅ Task Assigned Successfully!</div>
        <% } %>

        <h1>Assign Tasks to Employees</h1>
        
         <a href="Dashboard.jsp" class="btn">Back to Dashboard</a>
         <div style="clear: both;"></div>
         
        <table>
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Task Name</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (Task task : tasks) { %>
                    <tr>
                        <td><%= task.getId() %></td>
                        <td><%= task.getTaskName() %></td>
                        <td><%= task.getDescription() %></td>
                        <td><%= task.gettStatus() %></td>
                        <td>
                            <form action="AssignEmployeeServlet" method="post" style="display:inline;">
                                <input type="hidden" name="taskId" value="<%= task.getId() %>">
                                <select name="employeeId" required>
                                    <option value="">Select Employee</option>
                                    <% for (User employee : employees) { %>
                                        <option value="<%= employee.getId() %>"
                                            <%= (task.getEmployeeId() == employee.getId()) ? "selected" : "" %>>
                                            <%= employee.getName() %>
                                        </option>
                                    <% } %>
                                </select>
                                <button type="submit" class="assign-btn">Update</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
       
    </div>
</body>
</html>
