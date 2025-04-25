package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
                if (list.isEmpty()) {
                    throw new ServletException("問題が存在しません");
                }
                Collections.shuffle(list);
                session.setAttribute("list", list);
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("DB接続または取得エラー", e);
            }
        }

        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        if (currentIndex == null) {
            currentIndex = 0;
        }

        Integer score = (Integer) session.getAttribute("score");
        if (score == null) {
            score = 0;
        }

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

                    // セッションリセット
                    session.removeAttribute("list");
                    session.removeAttribute("items");
                    session.removeAttribute("score");
                    session.removeAttribute("correctStreak");
                    session.removeAttribute("currentIndex");

                } catch (Exception e) {
                    throw new ServletException("スコア保存エラー", e);
                }
            } else {
                scoreRecord = new Score();
                scoreRecord.setScore(score);
                scoreRecord.setPlayedAt(java.time.LocalDateTime.now());
            }

            // エピローグ処理
            String catName = (String) session.getAttribute("catName");
            String epilogueText;
            String epilogueTitle;

            if (score >= 100) {
                epilogueTitle = "エピローグ";
                epilogueText = "リン…リン…♪\n"
                        + catName + "ちゃんが持っていた鈴が、ひときわ明るく鳴り響きました。\n"
                        + "その音に導かれるように、森の木々の間から優しい光が差し込みます。\n"
                        + "「あっ…この道、見たことある！」\n"
                        + "「" + catName + "ちゃんっっ！」\n"
                        + "森のはずれで、お母さんが笑顔で手を振って待っていました。\n"
                        + "「よく頑張ったね。鈴の力と、" + catName + "ちゃんの力でちゃんと帰ってこれたんだね」\n"
                        + "こうして、" + catName + "ちゃんのちょっぴり不思議な一日は、そっと幕を下ろしました。\n";
            } else {
                epilogueTitle = "ゲームオーバー";
                epilogueText = "ゲームオーバー\n"
                        + "「うぅ…ダメだったのかな…家に帰りたい…お母さんに会いたい…」\n"
                        + "でも、森はまだそこにいて、やさしく見守ってくれているようでした。\n"
                        + "「だいじょうぶ。君なら、きっとまたがんばれるよ」\n"
                        + "次はもっと遠くまで、きっと行けるよ。\n";
            }

            session.setAttribute("epilogueTitle", epilogueTitle);
            session.setAttribute("catName", catName);
            session.setAttribute("epilogueText", epilogueText);
            session.setAttribute("score", scoreRecord);
            request.setAttribute("score", scoreRecord);
            request.setAttribute("user", user);
            if(score >= 100) {
            	request.getRequestDispatcher("/WEB-INF/jsp/epilogue.jsp").forward(request, response);
            }
            else {
            	request.getRequestDispatcher("/WEB-INF/jsp/gameover.jsp").forward(request, response);
            }
            return;
        }

        // 通常の問題表示
        Question question = list.get(currentIndex);
        request.setAttribute("question", question);
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

        Question question = list.get(currentIndex);
        String answerStr = request.getParameter("answer");
        Integer selected = null;
        boolean isCorrect = false;

        if (answerStr != null && !answerStr.equals("null")) {
            try {
                selected = Integer.parseInt(answerStr);
                if (selected == question.getCorrectChoice()) {
                    isCorrect = true;
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
                        } else {
                            if (r.nextBoolean()) {
                                items.put("matatabi", items.get("matatabi") + 1);
                            } else {
                                items.put("churu", items.get("churu") + 1);
                            }
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

        currentIndex++;
        session.setAttribute("score", score);
        session.setAttribute("currentIndex", currentIndex);

        // 解説表示用データを設定
        request.setAttribute("question", question);
        request.setAttribute("selected", selected);
        request.setAttribute("isCorrect", isCorrect);
        request.setAttribute("isLastQuestion", currentIndex >= list.size());

        // 解説ページを部分表示として返す
        request.getRequestDispatcher("/WEB-INF/jsp/answer.jsp").forward(request, response);
    }
}
