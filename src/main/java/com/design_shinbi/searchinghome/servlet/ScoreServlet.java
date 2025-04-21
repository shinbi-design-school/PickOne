package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.dao.ScoreDAO;
import com.design_shinbi.searchinghome.model.entity.Score;
import com.design_shinbi.searchinghome.util.DBUtil;

@WebServlet("/score")
public class ScoreServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBUtil.connect()) {
            HttpSession session = request.getSession();

            int userId = (int) session.getAttribute("userId");
            int scoreValue = (int) session.getAttribute("score");

            Score score = new Score(userId, scoreValue);
            ScoreDAO dao = new ScoreDAO(connection);
            dao.save(score);

            request.setAttribute("score", score);
            request.getRequestDispatcher("/WEB-INF/jsp/score.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

