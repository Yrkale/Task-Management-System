<%@ page import="com.taskmanagementsystem.dao.ProjectDAO" %>
<%@ page import="com.taskmanagementsystem.model.Project" %>
<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page import="java.util.List" %>
<%@ page session="true" %>

<%
    User manager = (User) session.getAttribute("loggedInUser");
    if (manager == null || !"manager".equalsIgnoreCase(manager.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    ProjectDAO projectDAO = new ProjectDAO();
    List<Project> managerProjects = projectDAO.getProjectsByManagerId(manager.getId());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Tasks</title> 
    <link rel="stylesheet" href="css/create_tasks.css">
</head>
<body>

 

<div class="form-container">
    <h2>Create Multiple Tasks</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="CreateTaskServlet" method="post">
        <label for="projectId">Select Project:</label>
        <select name="projectId" id="projectId" required>
            <option value="">-- Select Project --</option>
            <% 
                for (Project project : managerProjects) {
                    if (!"completed".equalsIgnoreCase(project.getpStatus()) && !"on hold".equalsIgnoreCase(project.getpStatus())) { 
            %>
                <option value="<%= project.getId() %>"><%= project.getName() %></option>
            <% 
                    }
                } 
            %>
        </select>

        <div id="taskContainer">
            <div class="task-block">
                <label>Task Name:</label>
                <input type="text" name="taskName" placeholder="Enter Task Name" required>
                
                <label>Description:</label>
                <textarea name="description" placeholder="Enter Task Description" required></textarea>
                
                <label>Priority:</label>
                <select name="priority" required>
                    <option value="">Select Priority</option>
                    <option value="Low">Low</option>
                    <option value="Medium">Medium</option>
                    <option value="High">High</option>
                </select>

                <label>Due Date:</label>
                <input type="date" name="dueDate" required>

                <button type="button" class="delete-btn" onclick="removeTaskBlock(this)">Delete Task</button>
            </div>
        </div>

        <div class="action-buttons">
            <button type="button" onclick="addTaskBlock()">+ Add Another Task</button>
            <button type="submit">Create All Tasks</button>
        </div>
    </form>

    <a href="Dashboard.jsp" class="back-link">&larr; Back to Dashboard</a>
</div>

<script>
    function addTaskBlock() {
        const container = document.getElementById("taskContainer");
        const newBlock = document.createElement("div");
        newBlock.className = "task-block";
        newBlock.innerHTML = `
            <label>Task Name:</label>
            <input type="text" name="taskName" placeholder="Enter Task Name" required>
            
            <label>Description:</label>
            <textarea name="description" placeholder="Enter Task Description" required></textarea>
            
            <label>Priority:</label>
            <select name="priority" required>
                <option value="">Select Priority</option>
                <option value="Low">Low</option>
                <option value="Medium">Medium</option>
                <option value="High">High</option>
            </select>

            <label>Due Date:</label>
            <input type="date" name="dueDate" required>

            <button type="button" class="delete-btn" onclick="removeTaskBlock(this)">Delete Task</button>
        `;
        container.appendChild(newBlock);
    }

    function removeTaskBlock(button) {
        const taskBlock = button.closest('.task-block');
        const container = document.getElementById("taskContainer");
        if (taskBlock && container.children.length > 1) {
            taskBlock.remove();
        } else {
            alert("You must keep at least one task block");
        }
    }
</script>

</body>
</html>
