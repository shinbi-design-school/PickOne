package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.dao.ScoreDAO;
import com.design_shinbi.searchinghome.model.entity.Score;
import com.design_shinbi.searchinghome.util.DBUtil;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBUtil.connect()) {
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId");

            ScoreDAO dao = new ScoreDAO(connection);
            List<Score> history = dao.findByUserId(userId);

            request.setAttribute("history", history);
            request.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

