<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>
<link rel="stylesheet" href="css/register.css">
</head>
<body>
	<div class="container">
		<h2>User Registration</h2>
		<form action="RegisterServlet" method="post">
			<label>Full Name:</label> <input type="text" name="username" required>

			<label>Email:</label> <input type="email" name="email" required>

			<label>Password:</label> <input type="password" name="password"	required> 
			
				
			<label>Role:</label> <select name="role" required>
				<option value="owner">Project Owner</option>
				<option value="manager">Manager</option>
				<option value="employee">Employee</option>
			</select> <input type="submit" value="Register">
			
		</form>
		<button onclick="window.location.href='login.jsp'">Already
			have an account? Login</button>
	</div>
</body>
</html>
