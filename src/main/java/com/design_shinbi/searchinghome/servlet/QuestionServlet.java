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

        List<Question> list = (List<Question>) session.getAttribute("list");
        if (list == null) {
            try (Connection connection = DBUtil.connect()) {
                QuestionDAO dao = new QuestionDAO(connection);
                list = dao.find10Questions();
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

        // 最後の問題が終わっていればリザルトへ
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
                    request.setAttribute("score", scoreRecord);
                } catch (Exception e) {
                    throw new ServletException("スコア保存エラー", e);
                }
            } else {
                scoreRecord = new Score();
                scoreRecord.setScore(score);
                scoreRecord.setPlayedAt(java.time.LocalDateTime.now());
            }
            String epilogueText = "";
            String epilogueTitle = "";
            String catName = (String)request.getSession().getAttribute("catName");

            if (score >= 70) {
                epilogueTitle = "エピローグ";
                epilogueText =  "リン…リン…♪\r\n"
                		+ catName + "ちゃんが持っていた鈴が、ひときわ明るく鳴り響きました。\r\n"
                		+ "\r\n"
                		+ "その音に導かれるように、森の木々の間から優しい光が差し込みます。\r\n"
                		+ "\r\n"
                		+ "「あっ…この道、見たことある！」\r\n"
                		+ "\r\n"
                		+ "光の道を進んでいくと、懐かしい風景がどんどん近づいてきて――\r\n"
                		+ "\r\n"
                		+ "「" + catName + "ちゃんっっ！」\r\n"
                		+ "\r\n"
                		+ "森のはずれで、お母さんが笑顔で手を振って待っていました。\r\n"
                		+ "\r\n"
                		+ "「よく頑張ったね。鈴の力と、" + catName + "ちゃんの力でちゃんと帰ってこれたんだね」\r\n"
                		+ "\r\n"
                		+ "ぎゅっと抱きしめられて、安心と嬉しさが胸いっぱいに広がります。\r\n"
                		+ "\r\n"
                		+ "こうして、" + catName + "ちゃんちゃんのちょっぴり不思議な一日は、\r\n"
                		+ "あたたかい夕陽とともに、そっと幕を下ろしました。\r\n"
                		+ "\r\n"
                		+ "";
            } else {
            	epilogueTitle = "ゲームオーバー";
                epilogueText ="ゲームオーバー\r\n"
                		+ "「うぅ…ダメだったのかな…家に帰りたい…お母さんに会いたい…」\r\n"
                		+ "\r\n"
                		+ "がっかりしてうつむいた" + catName + "ちゃんのまわりに、森の風がふわっと吹き抜けます。\r\n"
                		+ "\r\n"
                		+ "でも、森はまだそこにいて、やさしく見守ってくれているようでした。\r\n"
                		+ "\r\n"
                		+ "そのとき、鈴がかすかに光って、こうつぶやいたように聞こえました――\r\n"
                		+ "\r\n"
                		+ "「だいじょうぶ。君なら、きっとまたがんばれるよ」\r\n"
                		+ "\r\n"
                		+ "たしかに道は見つからなかったけれど、" +catName + "ちゃんの冒険はここで終わりじゃない。\r\n"
                		+ "森はいつでも君を待ってる。次はもっと遠くまで、きっと行けるよ。\r\n"
                		+ "\r\n"
                		+ "そう思ったら、ちょっぴり元気が出てきたのでした。\r\n"
                		+ "";
            }

            session.setAttribute("epilogueTitle", epilogueTitle);
            session.setAttribute("catName", catName);
            session.setAttribute("epilogueText", epilogueText);
            session.setAttribute("score", scoreRecord);
            request.setAttribute("score", scoreRecord);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/jsp/epilogue.jsp").forward(request, response);
            return;
        }

        // 「次の問題へ」からの遷移なら currentIndex++
        if ("true".equals(request.getParameter("proceed"))) {
            currentIndex++;
            session.setAttribute("currentIndex", currentIndex);
        }

        // 再取得
        if (currentIndex < list.size()) {
            Question question = list.get(currentIndex);
            request.setAttribute("question", question);
            request.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(request, response);
        } else {
            response.sendRedirect("question");
        }
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

        if (list == null || list.size() < 10) {
            // listがnullの場合はDBから問題を取得して設定
            try (Connection connection = DBUtil.connect()) {
                QuestionDAO dao = new QuestionDAO(connection);
                list = dao.find10Questions();
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
            response.sendRedirect("question");
            return;
        }

        String answerStr = request.getParameter("answer");
        if (answerStr != null) {
            try {
                int selected = Integer.parseInt(answerStr);
                Question question = list.get(currentIndex);

                boolean isCorrect = selected == question.getCorrectChoice();
                if (isCorrect) {
                    score += 10;
                }

                session.setAttribute("score", score);
                // 選択した回答と正誤を `answer.jsp` に渡す
                request.setAttribute("question", question);
                request.setAttribute("selected", selected);
                request.setAttribute("isCorrect", isCorrect);
                request.setAttribute("isLastQuestion", currentIndex + 1 >= list.size());

                // 次の問題に進むための処理（answer.jspに遷移）
                request.getRequestDispatcher("/WEB-INF/jsp/answer.jsp").forward(request, response);
                return;

            } catch (NumberFormatException e) {
                // 無効な選択肢（未選択など）→スキップ処理も可
                log("無効な選択肢が送信されました: " + answerStr);
            }
        } else {
            // answerがnullの場合のエラーハンドリング
            log("選択肢が選ばれませんでした");
        }

        // 正常な処理後、次の問題に進む
        currentIndex++;
        session.setAttribute("currentIndex", currentIndex);
        session.setAttribute("score", score);

        response.sendRedirect("question");
    }

}
