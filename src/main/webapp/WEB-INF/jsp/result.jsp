<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.User" %>


<%
    User user = (User)request.getAttribute("user");
    Score score = (Score) request.getAttribute("score");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String formattedPlayedAt = score.getPlayedAt().format(formatter);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>スコア結果</title>
</head>
<body>
    <h1>スコア結果</h1>
    <p><%= user.getName() %>さんのスコアは <strong><%= score.getScore() %></strong> 点です！</p>
    <p>プレイ日時：<%= formattedPlayedAt %></p>

    <p>
        <form action="ranking" method="get">
            <input type="submit" class="btn" value="ランキングを見る">
        </form>
        <form action="history" method="get">
            <input type="submit" class="btn" value="自分のスコア履歴">
        </form>
        <form action="top" method="get">
            <input type="submit" class="btn" value="トップに戻る">
        </form>
    </p>
</body>
</html>
