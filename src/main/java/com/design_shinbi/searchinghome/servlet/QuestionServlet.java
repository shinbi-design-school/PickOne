package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.Const;
import com.design_shinbi.searchinghome.model.dao.QuestionDAO;
import com.design_shinbi.searchinghome.model.dao.ScoreDAO;
import com.design_shinbi.searchinghome.model.entity.Question;
import com.design_shinbi.searchinghome.model.entity.Score;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute(Const.LOGIN_USER_KEY);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // セッションにクイズリストがない場合はDBから取得して設定
        List<Question> list = (List<Question>) session.getAttribute("list");
        if (list == null) {
            try (Connection connection = DBUtil.connect()) {
                QuestionDAO dao = new QuestionDAO(connection);
                list = dao.findAll();
                if (list.isEmpty()) {
                    throw new ServletException("問題が存在しません");
                }
                Collections.shuffle(list); // ランダム出題
                session.setAttribute("list", list); // リストをセッションに保存
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("DB接続または取得エラー", e);
            }
        }

        // currentIndexとscoreをセッションから取得し、nullの場合は初期値を設定
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        if (currentIndex == null) {
            currentIndex = 0; // 初期値を設定
        }

        Integer score = (Integer) session.getAttribute("score");
        if (score == null) {
            score = 0; // 初期値を設定
        }

        // 出題終了 → 結果ページへ
        if (currentIndex >= list.size()) {
            // 一度だけスコア登録
            if (session.getAttribute("scoreSaved") == null) {
                try (Connection connection = DBUtil.connect()) {
                    User loginUser = (User) session.getAttribute("loginUser");
                    int userId = loginUser.getId();
                    ScoreDAO dao = new ScoreDAO(connection);
                    Score scoreRecord = new Score(userId, score);
                    dao.save(scoreRecord);
                    
                    session.setAttribute("scoreSaved", true);
                } catch (Exception e) {
                    throw new ServletException("スコア保存時にエラーが発生しました", e);
                }
            }
            
            request.setAttribute("score", score);
            request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
            return;
        }

        // 現在の問題を表示
        Question question = list.get(currentIndex);
        request.setAttribute("question", question);
        request.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // セッションからリストやインデックスを取得、値がnullなら初期化
        List<Question> list = (List<Question>) session.getAttribute("list");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        Integer score = (Integer) session.getAttribute("score");

        if (list == null) {
            // listがnullの場合はDBから問題を取得して設定
            try (Connection connection = DBUtil.connect()) {
                QuestionDAO dao = new QuestionDAO(connection);
                list = dao.findAll();
                if (list.isEmpty()) {
                    throw new ServletException("問題が存在しません");
                }
                Collections.shuffle(list); // ランダム出題
                session.setAttribute("list", list); // リストをセッションに保存
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("DB接続または取得エラー", e);
            }
        }

        if (currentIndex == null) {
            currentIndex = 0;  // 初期値設定
        }
        if (score == null) {
            score = 0;  // 初期値設定
        }

        if (currentIndex >= list.size()) {
            response.sendRedirect("/WEB-INF/jsp/result.jsp");
            return;
        }

        String answerStr = request.getParameter("answer");
        if (answerStr != null) {
            try {
                int selected = Integer.parseInt(answerStr);
                Question question = list.get(currentIndex);

                if (selected == question.getCorrectChoice()) {
                    score += 10;
                }

            } catch (NumberFormatException e) {
                // 無効な選択肢（未選択など）→スキップ処理も可
                log("無効な選択肢が送信されました: " + answerStr);
            }
        } else {
            // answerがnullの場合のエラーハンドリング
            log("選択肢が選ばれませんでした");
        }

        currentIndex++;
        session.setAttribute("currentIndex", currentIndex);
        session.setAttribute("score", score);

        response.sendRedirect("question");
    }
}
