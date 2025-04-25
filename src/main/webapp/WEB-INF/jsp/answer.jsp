<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
    Integer selected = (Integer) request.getAttribute("selected");
    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    Boolean isLastQuestion = (Boolean) request.getAttribute("isLastQuestion");
    String contextPath = request.getContextPath();
%>

<div class="modal-content">
<h3><%= isCorrect ? "✅ 正解！" : "❌ 不正解…" %></h3>
<p>あなたの選択: <%= selected != null ? selected : "未選択" %></p>
<p>正解: <%= question.getCorrectChoice() %></p>
<p>解説: <%= question.getExplanation() %></p>

<% if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) { %>
    <img src="<%= contextPath + "/" + question.getImageUrl() %>" style="max-width: 300px;"><br>
<% } %>



<!-- 次の問題へ -->
<button id="nextQuestionBtn">
    <%= isLastQuestion ? "結果を見る" : "次の問題へ" %>
</button>
</div>
