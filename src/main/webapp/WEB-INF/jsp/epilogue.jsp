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
    String contextPath = request.getContextPath();
    request.setAttribute("title", "エピローグ");
    String catName = (String) session.getAttribute("catName");
    Score score = (Score) request.getAttribute("score"); 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDate = score.getPlayedAt().format(formatter);
%>
<!DOCTYPE html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
   <header></header>
    <main class="goal">
        <div class="naka">
            <div class="pro_txt">
                <img src="${pageContext.request.contextPath}/images/goal.png" class="ttl_goal">
                <p class="typeWriter">
                    光の道を進んでいくと、懐かしい風景がどんどん近づいてきて――<br>
                    「<%= catName %>ちゃんっっ！」<br>
                    森のはずれで、お母さんが笑顔で手を振って待っていました。<br>
                    「よく頑張ったね。鈴の力と、<%= catName %>ちゃんの力でちゃんと帰ってこれたんだね」<br>
                    ぎゅっと抱きしめられて、安心と嬉しさが胸いっぱいに広がります。<br>
                    こうして、<%= catName %>ちゃんのちょっぴり不思議な一日は、<br>
                    あたたかい夕陽とともに、そっと幕を下ろしました。
                </p>

            </div>
            <div class="score_board">
                <p><%= catName %>ちゃんのスコアは<br> <strong><%= score.getScore() %></strong> 点です！</p>
                <p>プレイ日時<br><%= formattedDate %></p>
            </div>
            <div class="login_form">
                <form action="top" method="post" target="content">
                <input type="submit" value="トップへ">
            </form>
            </div>
        </div>
        
        <img src="${pageContext.request.contextPath}/images/cat_home.png" class="cat_img04">
    </main>
    <script src="${pageContext.request.contextPath}/js/audioController.js"></script>
	<script>
        window.onload = function() {
            if (parent && parent.requestBGM) {
                parent.requestBGM('<%= request.getContextPath() %>/audio/goal.mp3');
            }
        };
    </script>
    
    <script src="js/typing.js"></script>
</body>
</html>
