<%@ page import="java.util.List" %>
<%@ page import="com.taskmanagementsystem.model.User" %>
<%@ page import="com.taskmanagementsystem.model.Project" %>
<%@ page import="com.taskmanagementsystem.dao.ProjectDAO" %>
<%@ page import="com.taskmanagementsystem.dao.UserDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- -------------------------------------------------------------------------------------- -->
<!-- common code for all three user -->

<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String name = user.getName();
    String role = user.getRole().toLowerCase(); // owner / manager / employee

    ProjectDAO projectDAO = new ProjectDAO(); // Declare only ONCE here
%>

<!-- -------------------------------------------------------------------------------------- -->
 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="css/ .css"> 

</head>


<body>

<!-- ---------------------------------------------------------------------------------------- -->
<!-- Profile icon -->
<div class="profile-icon" onclick="openModal()">👨‍💼</div>

<!-- profile View -->
<!-- ---------------------------------------------------------------------------------------- -->
<div class="modal" id="profileModal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Update Profile</h2>
        <form action="updateProfile" method="post">
            <div class="form-row">
                <!-- ID Row - inline -->
                <div class="form-group inline-group">
                    <label>Id:</label>
                    <span class="readonly-text"><%= user.getId() %></span>
                </div>
                
                <input type="hidden" name="id" value="<%= user.getId() %>" />

                <div class="form-group inline-group">
                    <label>Name:</label>
                    <span class="readonly-text"><%= user.getName() %></span>
                     
                </div>
                
                <input type="hidden" name="id" value="<%= user.getName() %>" />

                <div class="form-group inline-group">
                    <label>Email:</label>
                    <span class="readonly-text"><%= user.getEmail() %></span>
                    
                </div>
                
                <input type="hidden" name="id" value="<%= user.getEmail() %>" />
                
                <div class="form-group inline-group">
                    <label>Role</label>
                    <span class="readonly-text"><%= user.getRole() %></span>
                    
                </div>
            </div>

            <div class="checkbox-group">
                <input type="checkbox" id="changePasswordToggle" onclick="togglePasswordFields()" />
                <label for="changePasswordToggle">Change Password</label>
            </div>

            <div id="passwordFields" style="display: none;">
                <div class="form-row">
                    <div class="form-group">
                        <label>Old Password:</label>
                        <input type="password" name="oldPassword" />
                    </div>
                    <div class="form-group">
                        <label>New Password:</label>
                        <input type="password" name="newPassword" />
                    </div>
                </div>
            </div>

            <div class="button-group">
                <button type="submit">Update</button>
            </div>
        </form>
    </div>
</div>

<%
    String msg = (String) request.getAttribute("message");
    String err = (String) request.getAttribute("error");
%>

<% if (msg != null) { %>
    <div style="color: green; font-weight: bold; margin-bottom: 10px;"><%= msg %></div>
<% } else if (err != null) { %>
    <div style="color: red; font-weight: bold; margin-bottom: 10px;"><%= err %></div>
<% } %> 


<!-- profile View end  -->
<!-- ---------------------------------------------------------------------------------------- -->

<!-- -------------------------------------------------------------------------------------- -->
<!-- COMMON PANEL FOR ALL USERS -->


    <div class="dashboard-container">   
    
    <div class="welcome"> 
    
        <h1>Welcome, <%= name %>!</h1>         
        <p class="role">Role: <%= role.toUpperCase() %></p>
        
        
     </div>   
        
      

        
        
        
<!-- -------------------------------------------------------------------------------------- -->
<!-- Project Owner View  -->        
<% if ("project owner".equals(role)) { 
    List<Project> projectList = projectDAO.getAllProjects();
%>
    <div class="panel">
        <h2>Project Owner Panel</h2>  
        

        <h3>All Projects</h3>
        
        
        <div style="text-align: right;">
        <form action="LogoutServlet" method="post" style="margin-top: 2px; margin-left: 900px; margin-bottom: 3px">
   			 <button type="submit" class="logout-btn">Logout</button>
       </form>
       
    <button type="button" class="redirect_button" onclick="window.location.href='POCreateProject.jsp'">➕ Create and Assign Project</button>    
    <button type="button" class="redirect_button" onclick="window.location.href='POAddManager.jsp'">👤 Add Manager</button>
     
     </div>
        <table border="1" cellpadding="10">
         <thead>
            <tr>
                <th>ID</th>
                <th>Project Name</th>
                <th>Description</th>
                <th>Manager</th>
                <th>StartDate</th>
                <th>EndDate</th>
                <th>Progress</th>
                <th>Status</th>
                <th>Action</th> <!-- New Column for Update Button -->
            </tr>
             </thead>
             <tbody>
            <% for (Project project : projectList) { %>
                <tr>
                    <form action="UpdateProjectStatusServlet" method="post">
                        <td>
                            <%= project.getId() %>
                            <input type="hidden" name="projectId" value="<%= project.getId() %>">
                        </td>
                        <td><%= project.getName() %></td>
                        <td><%= project.getDescription() %></td>
                        <td><%= project.getManagerName() %></td>
                        <td><%= project.getStartDate() != null ? project.getStartDate() : "" %></td>
						<td><%= project.getEndDate() != null ? project.getEndDate() : "" %></td>
						
						
						
					
					
						 <td>
                                <input type="number" name="progress" min="0" max="100" value="<%= project.getProgressPercentage() %>" />%
                        </td>

                        <td>
                             <%= project.getpStatus() %>
                        </td>
                        <td>
                        <select name="pStatus">
                        
                                   <option value="">Not On Hold</option>                        
                                                    
                                 <option value="on hold" <%= "on hold".equalsIgnoreCase(project.getpStatus()) ? "selected" : "" %>>On Hold</option>
                            </select>
                            <button class="assign-btn" type="submit">Update</button>
                        </td>
                    </form>
                </tr>
            <% } %>
            </tbody>
        </table>
        
        </div>
        
     
<!-- Project Owner View End  -->
<!-- -------------------------------------------------------------------------------------- -->
    
    
<!-- -------------------------------------------------------------------------------------- -->
<!-- Manager View  -->

<% } else if ("manager".equals(role)) { 
    int managerId = user.getId();
    List<Project> assignedProjects = projectDAO.getProjectsByManagerId(managerId);
%>
<div class="panel">


    <h2>Manager Panel</h2>

    <h3>Your Assigned Projects</h3>
    
 
    
     
    
    
    <div style="text-align: right;">
     <form action="LogoutServlet" method="post" style="margin-top: 2px; margin-left: 900px; margin-bottom: 3px">
   			 <button type="submit" class="logout-btn">Logout</button>      
      </form>
      
     <button type="button" class="redirect_button" onclick="window.location.href='MCreateTasks.jsp'">➕ Create Task</button>
     <button type="button" class="redirect_button" onclick="window.location.href='MViewTasks.jsp'">🔍 See Task</button>  
   
    <button type="button" class="redirect_button" onclick="window.location.href='MAssignTasks.jsp'">📌 Assign Task</button>
    <button type="button" class="redirect_button" onclick="window.location.href='MAddEmployee.jsp'">👤 Add Employees</button>
   
    </div>
    
    
    
     
    <table border="1" cellpadding="10">
        <thead>
        <tr>
            <th>ID</th>
            <th>Project Name</th>
            <th>Description</th>
            <th>StartDate</th>
             <th>EndDate</th>
             <th>Progress</th>
             <th>Status</th>
            <th>Action</th>
        </tr>
         </thead>
         <tbody>
        <% for (Project project : assignedProjects) { %>
        <tr>
        
          <form action="UpdateProjectStatusServlet" method="post">
                    <input type="hidden" name="projectId" value="<%= project.getId() %>">
                    
            <td><%= project.getId() %></td>
            <td><%= project.getName() %></td>
            <td><%= project.getDescription() %></td>
             <td><%= project.getStartDate() != null ? project.getStartDate() : "" %></td>
			<td><%= project.getEndDate() != null ? project.getEndDate() : "" %></td>
			
			
			 <td>
                 <input type="number" name="progress" min="0" max="100" value="<%= project.getProgressPercentage() %>" />%
            </td>
			


            
            <td><%= project.getpStatus() != null ? project.getpStatus() : "Null" %></td>
            <td>
              
                    
                    <!-- this if use to restrist change of pstetus if is onhold set by p owner for manager -->
                   <% if(project.getpStatus() != null && !project.getpStatus().equalsIgnoreCase("on hold")) { %>
                    
                    <input type="hidden" name="pStatus">  
                    
                    <input class="assign-btn" type="submit" value="Update">
                    
                    <%} else {%>    
                    <h5>Onhold can not change </h5> 
                    
                    <%} %>           	
         
                    
                    
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    

</div>


<!-- Manager View End  -->
<!-- -------------------------------------------------------------------------------------- -->

<!-- -------------------------------------------------------------------------------------- -->
<!-- Employee View  -->

<div class="dashboard-container">
<% }  else if ("employee".equals(role)) { 
    int userId =  user.getId();  
%>

   <h2>Employee Dashboard</h2>
   <ul>
       <li>
           <form action="EViewTasks.jsp" method="get">
               <input type="hidden" name="status" value="ongoing">
               <input type="hidden" name="employeeId" value="<%= userId %>">
               <button type="submit" class="redirect_button" >View Tasks</button>
           </form>
       </li>
       <li>
           <form action="EViewAllRunningTastUnderhisManager.jsp" method="get">
               <input type="hidden" name="status" value="complete">
               <input type="hidden" name="employeeId" value="<%= userId %>">
               <button type="submit" class="redirect_button" >View All Running Tasks </button>
           </form>
       </li>
   </ul>
   <form action="LogoutServlet" method="post" style="margin-top: 20px; ">
   			 <button type="submit" class="logout-btn">Logout</button>
       </form>
</div>

<% } %>


  </div>

<!-- Employee View End -->
<!-- -------------------------------------------------- -->




<!-- ----------------------- Script ---------------------- -->

<script>
    function openModal() {
        document.getElementById("profileModal").style.display = "block";
    }

    function closeModal() {
        document.getElementById("profileModal").style.display = "none";
    }

    function togglePasswordFields() {
        const passFields = document.getElementById("passwordFields");
        passFields.style.display = document.getElementById("changePassCheckbox").checked ? "block" : "none";
    }

    // Optional: Close modal if user clicks outside the box
    window.onclick = function(event) {
        const modal = document.getElementById("profileModal");
        if (event.target === modal) {
            closeModal();
        }
    }
 
    function togglePasswordFields() {
        const passwordFields = document.getElementById("passwordFields");
        const checkbox = document.getElementById("changePasswordToggle");
        passwordFields.style.display = checkbox.checked ? "block" : "none";
    }

    function closeModal() {
        document.getElementById("profileModal").style.display = "none";
    }
 
    setTimeout(() => {
        const alerts = document.querySelectorAll("div[style*='color']");
        alerts.forEach(el => el.style.display = 'none');
    }, 3000); // hide after 3 seconds
</script>


<!-- -------------------------------------------------------------------------------------- -->
<!-- CSS file  -->

 <style>
    .profile-icon {
    position: fixed;
    top: 20px;
    right: 30px;
    font-size: 28px; /* slightly larger */
    width: 50px;
    height: 50px;
    background-color: #f0f0f0; /* light background */
    border: 2px solid #ccc;
    border-radius: 50%; /* makes it circular */
    text-align: center;
    line-height: 50px; /* vertically center the emoji */
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.2);
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.profile-icon:hover {
    background-color: #e0e0e0;
}

    .modal {
        display: none;
        position: fixed;
        z-index: 100;
        left: 0; top: 0;
        width: 100%; height: 100%;
        background-color: rgba(0,0,0,0.5);
    }
    .modal-content {
        background-color: white;
        margin: 10% auto;
        padding: 20px;
        width: 400px;
        border-radius: 10px;
        position: relative;
    }
    .close {
        position: absolute;
        top: 10px; right: 15px;
        font-size: 22px;
        cursor: pointer;
    }
    .redirect_button {
  background-color: #2d87f0;
  color: white;
  border: none;
  padding: 10px 18px;
  font-size: 15px;
  font-weight: 700;
  border-radius: 8px;
  margin: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.redirect_button:hover {
  background-color: #007BFF; /* Bootstrap Primary Blue */
  color: #fff;
  border: 1px solid #0056b3;
  transform: translateY(-2px);
  transition: all 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 123, 255, 0.3);
}

 

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
thead {
    background-color: #3498db;
    color: white;
     
}

table {
    width: 100%;
    border-collapse: collapse;
    margin: 0 auto 30px auto;
    font-size: 14px;
}

tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}

tbody tr:hover {
    background-color: #eaf4fc;
}





/* Smooth card-like wrapper for dashboard content */
.dashboard-container {
    max-width: 1100px;
    margin: 30px auto;
    padding: 20px 30px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 8px 18px rgba(0, 0, 0, 0.1);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Section headings */
.section-heading {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #2c3e50;
    border-left: 5px solid #3498db;
    padding-left: 10px;
}

/* Add subtle animation to table rows */
tbody tr {
    transition: all 0.2s ease-in-out;
}
tbody tr:hover {
    transform: scale(1.01);
}

/* Profile modal better spacing and visual clarity */
.modal-content input[type="text"],
.modal-content input[type="email"],
.modal-content input[type="password"] {
    width: 100%;
    padding: 8px;
    margin: 6px 0;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
}

/* Button group alignment */
.button-group {
    text-align: right;
    margin-top: 15px;
}

.button-group button {
    background-color: #3498db;
    color: #fff;
    padding: 8px 14px;
    border: none;
    border-radius: 6px;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.button-group button:hover {
    background-color: #2980b9;
}





.logout-btn {
    background-color: #e74c3c;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 15px;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.logout-btn:hover {
    background-color: #c0392b;
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(231, 76, 60, 0.3);
}

.inline-group {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 10px;
}

.readonly-text {
    font-weight: bold;
}

.readonly-input {
    border: none;
    background: transparent;
    font-weight: bold;
}

.welcome{
 
display: grid;
  justify-items: center;
  line-height: 4px;

}




</style>






</body>
</html>
