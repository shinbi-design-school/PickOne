package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        String item = request.getParameter("item");

	        @SuppressWarnings("unchecked")
			Map<String, Integer> items = (Map<String, Integer>) session.getAttribute("items");
	        if (items != null && items.containsKey(item) && items.get(item) > 0) {
	            items.put(item, items.get(item) - 1);

	            if("churu".equals(item)) {
	                session.setAttribute("timeMultiplier", 2);
	            }
	            session.setAttribute("items", items);
	        }
	 }
}
