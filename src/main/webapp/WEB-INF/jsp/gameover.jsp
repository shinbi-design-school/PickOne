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
    request.setAttribute("title", "ゲームオーバー");
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
                <img src="${pageContext.request.contextPath}/images/over_ttl.png" class="ttl_goal">
                <p class="typeWriter">
                    「うぅ…ダメだったのかな…家に帰りたい…お母さんに会いたい…」<br>
                    がっかりしてうつむいた<%= catName %>ちゃんのまわりに、森の風がふわっと吹き抜けます。<br>
                    でも、森はまだそこにいて、やさしく見守ってくれているようでした。<br>
                    そのとき、鈴がかすかに光って、こうつぶやいたように聞こえました――<br>
                    「だいじょうぶ。君なら、きっとまたがんばれるよ」<br>
                    たしかに道は見つからなかったけれど、<%= catName %>ちゃんの冒険はここで終わりじゃない。<br>
                    森はいつでも君を待ってる。次はもっと遠くまで、きっと行けるよ。<br>
                    そう思ったら、ちょっぴり元気が出てきたのでした。
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
        
        <img src="${pageContext.request.contextPath}/images/cat_komari.png" class="cat_img05">
    </main>
    
    <script src="${pageContext.request.contextPath}/js/audioController.js"></script>
	<script>
        window.onload = function() {
            if (parent && parent.requestBGM) {
                parent.requestBGM('<%= request.getContextPath() %>/audio/gameover.mp3');
            }
        };
    </script>
    
    <script src="js/typing.js"></script>
</body>
</html>
