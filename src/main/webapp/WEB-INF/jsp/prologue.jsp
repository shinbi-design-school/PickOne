<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("title", "プロローグ");
    String catName = (String) session.getAttribute("catName");
%>

<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="prologue">
        <div class="naka">
            <div class="prolo_txt">
            <img src="${pageContext.request.contextPath}/images/prologue.png" class="ttl_pro">
                <p class="typeWriter">
                ポカポカ陽気に誘われて、森へお出かけした<%= catName %>ちゃん。<br>
				蝶々と遊びたくて追いかけて…追いかけて…気がつけば、いつの間にか森の奥深くまで来てしまいました。<br>
				「…あれ？ここ、どこ？」<br>
				遊び慣れた森なのに、いつもと違って見えて、迷子になってしまったみたい。<br>
				「おかあさんに会いたいよ…お腹もすいてきたよ…」<br>
				そんなとき、<%= catName %>ちゃんはふと、お母さんの言葉を思い出しました。<br>
				「そうだ…お誕生日にもらったこの鈴…<br>
				“クイズに正解していけば、おうちまで導いてくれる”って、お母さん言ってたっけ…！」<br>
				一人で心細いけど、大好きなお母さんに会いたいから――<br>
				<%= catName %>ちゃんは勇気を出して、クイズに挑むことを決めました。
                </p>
            </div>
            <div class="nav_area btn_nav">
                <a href="${pageContext.request.contextPath}/question" target="content">
                    次へ</a>
                </a>
            </div>
        </div>
        <img src="${pageContext.request.contextPath}/images/cat_jump.png" class="cat_img02 fuwari">
        <img src="${pageContext.request.contextPath}/images/icegif-862.gif" class="tyou_img">
    </main>
    <jsp:include page="footer.jsp" />
    <script>
        window.onload = function() {
            requestBGM('<%= request.getContextPath() %>/audio/prologue.mp3');
        };
    </script>
    <script src="js/typing.js"></script>
</body>
</html>