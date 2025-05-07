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
    if (isLastQuestion == null) isLastQuestion = false;
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
    session.setAttribute("timeMultiplier", 1);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>ただいまをさがして-クイズ</title>
</head>
<body>
    <header></header>
    <main class="top quiz flex_box">
        <div class="q_box">
            <img src="${pageContext.request.contextPath}/images/ttl_q.png" class="ttl_question">
            <p><%= question.getQuestionText() %></p>
        </div>


        <form id="answerForm" action="question" method="post" class="flex_box" target="content">
            <input type="hidden" name="answer" id="answerInput">
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
            <a href="#" class="mttb_box" id="matatabiUsage"><img src="${pageContext.request.contextPath}/images/matatabi.png">× <%= items.get("matatabi") %></a>
            <a href="#" class="tyuru_box" id="churuUsage"><img src="${pageContext.request.contextPath}/images/tyu-ru.png">× <%= items.get("churu") %></a>
        </div>
        </form>

        

        <img src="${pageContext.request.contextPath}/images/cat_ques.png" class="cat_img02">

        <!-- モーダルここから -->
        <div class="modal-container">
            <div class="modal-body">
                <div class="modal-close">×</div>
                <div class="modal-content">
                   <p class="red">正解は <%= question.getCorrectChoice() %> 番！</p>
                    <p class="ans_pht"><img src="<%= request.getContextPath() + "/" + question.getImageUrl() %>"></p>
                    <p class="kaisetsu"><%= question.getExplanation() %></p>
                    <div class="right">
                        <a href="#" class="go-next">
                            <%= nextButtonImage %> 
                        </a>
                    </div>
                    <img src="${pageContext.request.contextPath}/images/cat_ans.png" class="cat_img03">
                </div>
            </div>
        </div>
        <!-- モーダルここまで -->
    </main>
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
        const contextPath = '<%= request.getContextPath() %>';
    </script>
    <script src="${pageContext.request.contextPath}/js/audioController.js"></script>
	<script>
        window.onload = function() {
            if (parent && parent.requestBGM) {
                parent.requestBGM('<%= request.getContextPath() %>/audio/question.mp3');
            }
        };
    </script>
    <script>
    window.correctChoice = <%= question.getCorrectChoice() %>;
    </script>
    <script src="${pageContext.request.contextPath}/js/question.js"></script>
    <script src="${pageContext.request.contextPath}/js/item.js">
</body>
</html>

