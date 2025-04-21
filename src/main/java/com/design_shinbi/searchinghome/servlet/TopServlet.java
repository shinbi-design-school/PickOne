package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.Const;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;


@WebServlet("/top")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	    	HttpSession session = request.getSession();
	    	
	    	User user = (User)session.getAttribute(Const.LOGIN_USER_KEY);
	    	
	    	String jsp = null;
	    	if(user == null) {
	    		jsp = "/WEB-INF/jsp/login.jsp";
	    	}
	    	else {
	    		try{
	    			Connection connection = DBUtil.connect();
	    			jsp = "/WEB-INF/jsp/top.jsp";
	    			connection.close();
	    		}
	    		catch(Exception e) {
	    			throw new ServletException(e);
	    		}
	    	}
	    	
	    	try {
	    		RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
	    		dispatcher.forward(request, response);
	    	}
	    	catch(Exception e) {
	    		throw new ServletException(e);
	    	}
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        request.setCharacterEncoding("UTF-8");

	        String catName = request.getParameter("catName");

	        HttpSession session = request.getSession();
	        session.setAttribute("catName", catName);

	        response.sendRedirect("question"); 
	    }
	}

