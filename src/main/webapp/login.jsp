<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <div class="container">
        <h1>User Login</h1>

        <%
            String loginError = (String) session.getAttribute("loginError");
            if (loginError != null && !loginError.isEmpty()) {
        %>
            <p class="error"><%= loginError %></p>
        <%
                session.removeAttribute("loginError");
            }
        %>

        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Login</button>
        </form>

        <div class="links">
           <!--  <p>Don't have an account? <a href="register.jsp">Register here</a></p> -->
            <p><a href="index.jsp">Back to Home</a></p>
        </div>
    </div>
</body>
</html>
