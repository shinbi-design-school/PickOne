package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

	    List<Question> list = (List<Question>) session.getAttribute("list");
	    if (list == null) {
	        try (Connection connection = DBUtil.connect()) {
	            QuestionDAO dao = new QuestionDAO(connection);
	            list = dao.find15Questions();
	            Collections.shuffle(list);
	            session.setAttribute("list", list);
	        } catch (Exception e) {
	            throw new ServletException("DB取得エラー", e);
	        }
	    }

	    Integer currentIndex = (Integer) session.getAttribute("currentIndex");
	    if (currentIndex == null) currentIndex = 0;

	    // スコアが未設定なら初期化
	    Integer score = (Integer) session.getAttribute("score");
	    if (score == null) {
	        score = 0;
	        session.setAttribute("score", score);
	    }

	    // 「次の問題へ」クリック時に進行
	    if ("true".equals(request.getParameter("next"))) {
	        currentIndex++;
	        session.setAttribute("currentIndex", currentIndex);
	    }

	    // ゲーム終了判定
	    if (currentIndex >= list.size()) {
	        Score scoreRecord;

	        if (session.getAttribute("scoreSaved") == null) {
	            try (Connection connection = DBUtil.connect()) {
	                ScoreDAO dao = new ScoreDAO(connection);
	                scoreRecord = new Score(user.getId(), score);
	                dao.save(scoreRecord);
	                scoreRecord.setPlayedAt(java.time.LocalDateTime.now());
	                session.setAttribute("score", scoreRecord);
	                session.setAttribute("scoreSaved", true);
	            } catch (Exception e) {
	                throw new ServletException("スコア保存エラー", e);
	            }
	        } else {
	            scoreRecord = new Score();
	            scoreRecord.setScore(score);
	            scoreRecord.setPlayedAt(java.time.LocalDateTime.now());
	        }

	        String catName = (String) session.getAttribute("catName");           
	        session.setAttribute("catName", catName);
	        request.setAttribute("score", scoreRecord);
	        request.setAttribute("user", user);

	        if (score >= 100) {
	            request.getRequestDispatcher("/WEB-INF/jsp/epilogue.jsp").forward(request, response);
	        } else {
	            request.getRequestDispatcher("/WEB-INF/jsp/gameover.jsp").forward(request, response);
	        }
	        return;
	    }

	    Question question = list.get(currentIndex);
	    request.setAttribute("question", question);
	    boolean isLast = currentIndex == (list.size() - 1);
	    request.setAttribute("isLastQuestion", isLast);

	    request.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(request, response);
	}


    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Question> list = (List<Question>) session.getAttribute("list");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        Integer score = (Integer) session.getAttribute("score");
        Map<String, Integer> items = (Map<String, Integer>) session.getAttribute("items");

        if (list == null || list.isEmpty()) {
            response.sendRedirect("question");
            return;
        }
        if (currentIndex == null) currentIndex = 0;
        if (score == null) score = 0;
        
        // アイテム使用処理
        String useItem = request.getParameter("useItem");
        if (useItem != null) {
            if (items == null) {
                items = new HashMap<>();
                items.put("matatabi", 0);
                items.put("churu", 0);
            }

            // アイテム使用
            if (useItem.equals("matatabi") && items.get("matatabi") > 0) {
                items.put("matatabi", items.get("matatabi") - 1);  
            } else if (useItem.equals("churu") && items.get("churu") > 0) {
                items.put("churu", items.get("churu") - 1);  
            }
            session.setAttribute("items", items);
        }
        
        Question question = list.get(currentIndex);
        String answerStr = request.getParameter("answer");
        Integer selected = null;

        if (answerStr != null && !answerStr.trim().isEmpty()) {
            try {
                selected = Integer.parseInt(answerStr);

                if (selected == question.getCorrectChoice()) {
                    score += 10;

                    Integer correctStreak = (Integer) session.getAttribute("correctStreak");
                    if (correctStreak == null) correctStreak = 0;
                    correctStreak++;

                    if (correctStreak == 4) {
                        correctStreak = 0;
                        Random r = new Random();
                        boolean both = r.nextInt(100) < 1;

                        if (items == null) {
                            items = new HashMap<>();
                            items.put("matatabi", 0);
                            items.put("churu", 0);
                        }

                        if (both) {
                            items.put("matatabi", items.get("matatabi") + 1);
                            items.put("churu", items.get("churu") + 1);
                        } else if (r.nextBoolean()) {
                            items.put("matatabi", items.get("matatabi") + 1);
                        } else {
                            items.put("churu", items.get("churu") + 1);
                        }
                        session.setAttribute("items", items);
                    }

                    session.setAttribute("correctStreak", correctStreak);
                } else {
                    session.setAttribute("correctStreak", 0);
                }

            } catch (NumberFormatException e) {
                log("数値変換エラー：" + answerStr);
            }
        } else {
            log("未選択 or null が送信されました");
        }
        session.setAttribute("score", score);
        session.setAttribute("currentIndex", currentIndex);
        response.sendRedirect("question?next=true");
    }
}
