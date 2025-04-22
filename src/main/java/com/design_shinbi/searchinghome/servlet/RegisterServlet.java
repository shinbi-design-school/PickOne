package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.design_shinbi.searchinghome.model.Const;
import com.design_shinbi.searchinghome.model.dao.UserDAO;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String error = null;
		
		if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
			error = "すべての項目を入力してください。";
		}
		
		if(error == null) {
			try(Connection connection = DBUtil.connect()){
				UserDAO dao = new UserDAO(connection);
				
				if(dao.findByEmail(email) != null) {
					error = "このメールアドレスは既に使われています。";
				}
				else {
					User user = new User();
					user.setName(name);
					user.setEmail(email);
					user.setPassword(password);
					user.setAdmin(false);
					dao.add(name, email, password, false);
					request.getSession().setAttribute(Const.LOGIN_USER_KEY, user);
					response.sendRedirect("top");
					return;
				}
			}catch(Exception e) {
				throw new ServletException("登録時にエラーが発生しました。");
			}
		}
		request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
	}

}
