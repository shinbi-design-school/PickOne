<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%@ page import="java.util.*" %>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
    Question question = (Question) request.getAttribute("question");
    String contextPath = request.getContextPath();
    Boolean isLastQuestion = (Boolean) request.getAttribute("isLastQuestion");
    String nextButtonImage = isLastQuestion
            ? "<img src='" + contextPath + "/images/btn_next.png' class='btn_next'>"
            : "<img src='" + contextPath + "/images/btn_nextq.png' class='btn_next'>";
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
    <jsp:include page="head.jsp" />
   
</head>
<body>
<header></header>
    <main class="top quiz flex_box">
        <div class="q_box">
            <img src="${pageContext.request.contextPath}/images/ttl_q.png" class="ttl_question">
            <p><%= question.getQuestionText() %>
            </p>
        </div>

        <div class="a_area flex_box">
            <a class="a_box modal-open" href="#" data-answer="1">
                <img src="${pageContext.request.contextPath}/images/a1.png" class="ttl_anser">
                <p><%= question.getChoice1() %></p>
            </a>
            <a class="a_box modal-open" href="#" data-answer="2">
                <img src="${pageContext.request.contextPath}/images/a2.png" class="ttl_anser">
                <p><%= question.getChoice2() %></p>
            </a>
            <a class="a_box modal-open" href="#" data-answer="3">
                <img src="${pageContext.request.contextPath}/images/a3.png" class="ttl_anser">
                <p><%= question.getChoice3() %></p>
            </a>
            <a class="a_box modal-open" href="#" data-answer="4">
                <img src="${pageContext.request.contextPath}/images/a4.png" class="ttl_anser">
                <p><%= question.getChoice4() %></p>
            </a>
        </div>
        <div class="op_box">
            <div class="time_box"><p class="naka"><%= totalTime %></p></div>
            <a href="#" class="mttb_box"><img src="${pageContext.request.contextPath}/images/matatabi.png">× 0</a>
            <a href="#" class="tyuru_box"><img src="${pageContext.request.contextPath}/images/tyu-ru.png">× 0</a>
        </div>
        <img src="${pageContext.request.contextPath}/images/cat_ques.png" class="cat_img02">

        <!--ここから解答-->
        <div class="modal-container">
            <div class="modal-body">
                <div class="modal-close">×</div>
                <div class="modal-content">
                    <p class="red">正解は<%= question.getCorrectChoice() %>番！</p>

                    <p class="ans_pht"><img src="<%= contextPath + "/" + question.getImageUrl()%>"></p>
                    <p class="kaisetsu"><%= question.getExplanation() %></p>
                    <div class="right">
                        <a href="#" class="extra-close" data-next="true">
                        <%= nextButtonImage %>
                        </a>
                    </div>
                    <img src="${pageContext.request.contextPath}/images/cat_ans.png" class="cat_img03">
                </div>
            </div>
        </div>
        <!--ここまで解答-->
    </main>
    <footer></footer>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="js/modal.js"></script>
    <script src="<%= contextPath %>/js/question.js"></script>
    </body>
</html>
