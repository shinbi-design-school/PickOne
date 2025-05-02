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
import com.design_shinbi.searchinghome.model.dao.UserDAO;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            Connection connection = DBUtil.connect();
            String jsp = null;
            boolean error = false;
            User user = null;

            UserDAO userDao = new UserDAO(connection);
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            	error = true;
            	jsp = "/WEB-INF/jsp/login-error.jsp";
            }              

            if (!error) {
                user = userDao.login(email, password);
                if(user == null) {
                	jsp = "/WEB-INF/jsp/login-error.jsp";
                }
                if(jsp != null)request.getRequestDispatcher(jsp).forward(request, response);
	
	            HttpSession oldSession = request.getSession(false);
	            if(oldSession != null) {
	            	oldSession.invalidate();
	            }
	            HttpSession newSession = request.getSession(true);
	            newSession.setAttribute(Const.LOGIN_USER_KEY, user);
	            jsp = "/WEB-INF/jsp/top.jsp";
	        }

            RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
