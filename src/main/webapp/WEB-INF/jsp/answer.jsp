<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
    Integer selected = (Integer) request.getAttribute("selected");
    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    Boolean isLastQuestion = (Boolean)request.getAttribute("isLastQuestion");
    String contextPath = request.getContextPath();  // contextPath は request から取得
    if (isCorrect == null) {
        isCorrect = false;
    }
    if(isLastQuestion == null){
    	isLastQuestion = false;
    }
%>
<html>
<head><title>解説</title></head>
<body>
    <h2><%= isCorrect ? "正解！" : "不正解…" %></h2>
    <p>あなたの選択: <%= selected != null ? selected : "未選択" %></p>
    <p>正解: <%= question != null ? question.getCorrectChoice() : "不明" %></p>
    <p>解説: <%= question != null ? question.getExplanation() : "解説なし" %></p>

    <% if (question != null && question.getImageUrl() != null && !question.getImageUrl().isEmpty()) { %>
        <img src="<%= contextPath + "/" + question.getImageUrl() %>" alt="クイズ画像" style="max-width:300px;"><br>
    <% } %>

    <form action="question" method="get">
       <% if (isLastQuestion) { %>
            <input type="hidden" name="proceed" value="true">
            <button type="submit">結果を見る</button>
        <% } else { %>
            <input type="hidden" name="proceed" value="true">
            <button type="submit">次の問題へ</button>
        <% } %>
    </form>
</body>
</html>
