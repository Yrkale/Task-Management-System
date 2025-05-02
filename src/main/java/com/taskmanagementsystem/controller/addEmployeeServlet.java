package com.taskmanagementsystem.controller;

import java.io.IOException;

import com.taskmanagementsystem.dao.UserDAO;
import com.taskmanagementsystem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

 

@WebServlet("/addEmployeeServlet")
public class addEmployeeServlet extends HttpServlet {
	
	private static final long serialVersionUID=1L;
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException{
		userDAO = new UserDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String role=request.getParameter("role");       // should be "EMPLOYEE"
		
	    String managerIdStr = request.getParameter("manager_id");
	    
	    if (name == null || email == null || password == null || role == null || managerIdStr == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("MAddEmployee.jsp").forward(request, response);
            return;
        }
	    
	    int managerId = Integer.parseInt(managerIdStr);

	   
		
		User employee = new User(name, email, password , role);
		
		 employee.setManagerId(managerId);
		
		boolean success;
		try {
			success = userDAO.registerUser(employee);		
		
		if(success) {
			response.sendRedirect("Dashboard.jsp");
		}
		
		else {
			request.setAttribute("error", "Failed to add Employee. ");
			request.getRequestDispatcher("MAddEmployee.jsp").forward(request, response);
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
