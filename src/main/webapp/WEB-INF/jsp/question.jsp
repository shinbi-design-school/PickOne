<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
    Integer selected = (Integer) session.getAttribute("selectedAnswer");
    String contextPath = request.getContextPath(); // 追加：contextPathを定義
%>
<html>
<head><title>クイズ</title></head>
<body>
    <h2><%= question.getQuestionText() %></h2>

    

    <form action="question" method="post">
        <input type="radio" name="answer" value="0" <%= selected != null && selected == 0 ? "checked" : "" %> required> <%= question.getChoice1() %><br>
        <input type="radio" name="answer" value="1" <%= selected != null && selected == 1 ? "checked" : "" %> > <%= question.getChoice2() %><br>
        <input type="radio" name="answer" value="2" <%= selected != null && selected == 2 ? "checked" : "" %> > <%= question.getChoice3() %><br>
        <input type="radio" name="answer" value="3" <%= selected != null && selected == 3 ? "checked" : "" %> > <%= question.getChoice4() %><br>
        <input type="submit" value="回答する">
    </form>
</body>
</html>
