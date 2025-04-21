<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
    String contextPath = request.getContextPath(); // アプリケーションルート
%>
<html>
<head><title>クイズ</title></head>
<body>
    <h2><%= question.getQuestionText() %></h2>

    <% if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) { %>
        <img src="<%= contextPath + "/" + question.getImageUrl() %>" alt="クイズ画像" style="max-width:300px;"><br>
    <% } %>

    <form action="question" method="post">
        <input type="radio" name="answer" value="0" required> <%= question.getChoice1() %><br>
        <input type="radio" name="answer" value="1"> <%= question.getChoice2() %><br>
        <input type="radio" name="answer" value="2"> <%= question.getChoice3() %><br>
        <input type="radio" name="answer" value="3"> <%= question.getChoice4() %><br>
        <input type="submit" value="回答する">
    </form>
</body>
</html>

