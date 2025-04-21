package com.design_shinbi.searchinghome.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.Const;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		HttpSession session = request.getSession();
		session.removeAttribute(Const.LOGIN_USER_KEY);

		String jsp = "/WEB-INF/jsp/login.jsp";

		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}