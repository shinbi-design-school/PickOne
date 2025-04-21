package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.design_shinbi.searchinghome.model.dao.ScoreDAO;
import com.design_shinbi.searchinghome.model.entity.Score;
import com.design_shinbi.searchinghome.util.DBUtil;

@WebServlet("/ranking")
public class RankingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBUtil.connect()) {
            ScoreDAO dao = new ScoreDAO(connection);
            List<Score> topScores = dao.findTopScore(10); // 上位10件

            request.setAttribute("ranking", topScores);
            request.getRequestDispatcher("/WEB-INF/jsp/ranking.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
