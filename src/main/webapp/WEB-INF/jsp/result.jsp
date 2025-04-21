<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
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
    <p>あなたのスコアは <strong><%= score.getScore() %></strong> 点です！</p>
    <p>プレイ日時：<%= formattedPlayedAt %></p>

    <p>
        <a href="ranking">ランキングを見る</a> |
        <a href="history">自分のスコア履歴</a> |
        <a href="index.jsp">トップへ戻る</a>
    </p>
</body>
</html>
