package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/epilogue")
public class EpilogueServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String catName = (String)request.getSession().getAttribute("catName");
		request.setAttribute("catName", catName);
		String epilogueText = (String)request.getSession().getAttribute("epilogueText");
		request.setAttribute("epilogueText", epilogueText);
		request.getRequestDispatcher("WEB-INF/jsp/epilogue.jsp").forward(request, response);
    }
}
