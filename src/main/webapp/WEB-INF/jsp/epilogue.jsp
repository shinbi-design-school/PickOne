<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.User" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    String epilogueTitle = (String)session.getAttribute("epilogueTitle");
    String catName = (String) session.getAttribute("catName");
    Score score = (Score) request.getAttribute("score"); 
    String epilogueText = (String) session.getAttribute("epilogueText");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String formattedPlayedAt = score.getPlayedAt().format(formatter); // OK now
%>
<html>
<head><title><%= epilogueTitle %></title></head>
<body>
   
    <p><%= user.getName() %>さんのスコアは <strong><%= score.getScore() %></strong> 点です！</p>
    <p>プレイ日時：<%= formattedPlayedAt %></p>
    <p><%= epilogueText %></p>
    <form action="top" method="post">
        <input type="submit" class="btn" value="タイトルへ">
    </form>
</body>
</html>
