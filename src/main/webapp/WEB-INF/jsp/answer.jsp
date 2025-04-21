<%@ page contentType="text/html;charset=UTF-8" %>
<%
    com.design_shinbi.searchinghome.model.entity.Question question =
        (com.design_shinbi.searchinghome.model.entity.Question) request.getAttribute("question");
    int selected = (int) request.getAttribute("selected");
    boolean isCorrect = (boolean) request.getAttribute("isCorrect");
%>
<html>
<head><title>解説</title></head>
<body>
    <h2><%= isCorrect ? "正解！" : "不正解…" %></h2>
    <p>あなたの選択: <%= selected %></p>
    <p>正解: <%= question.getCorrectChoice() %></p>
    <p>解説: <%= question.getExplanation() %></p>
    <img src="<%= question.getImagePath() %>" alt="クイズ画像" style="max-width:300px;"><br>

    <form action="question" method="get">
        <input type="hidden" name="proceed" value="true">
        <input type="hidden" name="lastSelected" value="<%= selected %>">
        <button type="submit">次の問題へ</button>
    </form>
</body>
</html>
