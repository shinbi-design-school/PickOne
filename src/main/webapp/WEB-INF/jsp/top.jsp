<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.User" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("title", "メニュー");
    User user = (User) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top menu">
        <div class="naka">
            <div class="tuto_txt">
                <img src="${pageContext.request.contextPath}/images/tutorial02.png" class="ttl_tuto">
                
                <p class="">
                全15問のクイズにチャレンジ！
                    10問以上正解すると、猫ちゃんは無事におうちへ帰ることができるよ。<br>
                    さらに…
                    4問連続で正解すると、冒険の役に立つアイテムがランダムでもらえる！<br>
                    もらえるアイテム<br>
                    マタタビ：選択肢が半分に減る！（2択になるよ）<br>
                    チュール：制限時間がちょっと延びる！<br>
                    準備はいい？
                    さあ、猫ちゃんと一緒にクイズの冒険へ出発しよう！
                </p>
            </div>
            <div class="flex_box nav_area btn_nav menu_btn" >
             <a href="${pageContext.request.contextPath}/history">
                 プレイ履歴</a>
             </a>
             <a href="${pageContext.request.contextPath}/naming">
                 スタート</a>
             </a>
             <a href="${pageContext.request.contextPath}/ranking">
                 ランキング</a>
             </a>
             </div>
         </div>
         <img src="${pageContext.request.contextPath}/images/cat_nobi02.png" class="cat_img02">
         </main>
         <footer></footer>
         <script src="<%= request.getContextPath() %>/js/audioController.js"></script>
         <script>
        window.onload = function() {
            playBGM('<%= request.getContextPath() %>/audio/index.mp3');
        };
    </script>
</body>
</html>
