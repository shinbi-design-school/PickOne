package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/prologue")
public class PrologueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String catName = (String)request.getSession().getAttribute("catName");
		request.setAttribute("catName", catName);
		String prologueText = (String)request.getSession().getAttribute("prologueText");
		request.setAttribute("prologueText", prologueText);
		request.getRequestDispatcher("WEB-INF/jsp/prologue.jsp").forward(request, response);
	}
	
}
