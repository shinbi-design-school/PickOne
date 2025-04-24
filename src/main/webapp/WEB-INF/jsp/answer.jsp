<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
    Integer selected = (Integer) request.getAttribute("selected");
    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    Boolean isLastQuestion = (Boolean) request.getAttribute("isLastQuestion");
    String contextPath = request.getContextPath();
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>解説</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<h3><%= isCorrect ? "✅ 正解！" : "❌ 不正解…" %></h3>
<p>あなたの選択: <%= selected != null ? selected : "未選択" %></p>
<p>正解: <%= question.getCorrectChoice() %></p>
<p>解説: <%= question.getExplanation() %></p>

<% if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) { %>
    <img src="<%= contextPath + "/" + question.getImageUrl() %>" style="max-width: 300px;"><br>
<% } %>

<p style="color: gray; font-style: italic;">※ 現在、タイマーは一時停止中です</p>

<!-- 次の問題へ -->
<button id="nextQuestionBtn">
    <%= isLastQuestion ? "結果を見る" : "次の問題へ" %>
</button>

<script>
    // 次の問題へ進む処理（question.jspのJSと連携）
    $('#nextQuestionBtn').on('click', function() {
        window.location.href = 'question?proceed=true';
    });
</script>

</body>
</html>
