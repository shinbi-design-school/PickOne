<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%@ page import="java.util.*" %>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
    Question question = (Question) request.getAttribute("question");
    String contextPath = request.getContextPath();

    Map<String, Integer> items = (Map<String, Integer>) session.getAttribute("items");
    if (items == null) {
        items = new HashMap<>();
        items.put("matatabi", 0);
        items.put("churu", 0);
    }

    Integer timeMultiplier = (Integer) session.getAttribute("timeMultiplier");
    if (timeMultiplier == null) {
        timeMultiplier = 1;
    }
    int baseTime = 10;
    int totalTime = baseTime * timeMultiplier;

    // タイムマルチプライヤーは使い切りなので、ここでリセット
    session.setAttribute("timeMultiplier", 1);
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>クイズ</title> 
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <jsp:include page="head.jsp" />
    <script src="<%= contextPath %>/js/question.js"></script>
</head>
<body>

<h2 id="questionText" data-correct="<%= question.getCorrectChoice() %>"><%= question.getQuestionText() %></h2>
<div id="timer">残り時間: <span id="time" data-time="<%= totalTime %>"><%= totalTime %></span> 秒</div>


<!-- アイテム使用ボタン -->
<div>
    <p>
        マタタビ: <%= items.get("matatabi") %> / チュール: <%= items.get("churu") %>
    </p>
    <button id="useMatatabi" <%= items.get("matatabi") <= 0 ? "disabled" : "" %>>マタタビを使う</button>
    <button id="useChuru" <%= items.get("churu") <= 0 ? "disabled" : "" %>>チュールを使う</button>
</div>

<form id="quizForm">
    <div class="choice"><label><input type="radio" name="answer" value="1" required> <%= question.getChoice1() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="2"> <%= question.getChoice2() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="3"> <%= question.getChoice3() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="4"> <%= question.getChoice4() %></label></div>
    <button type="submit">回答する</button>
</form>

<div class="modal-container">
  <div class="modal-body">
    <a class="modal-close" href="#">×</a>
    <div id="resultModal">
    </div>
  </div>
</div>

</body>
</html>
