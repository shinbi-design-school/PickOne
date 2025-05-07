<%@ page contentType="text/html; charset=UTF-8" %>
<%
    request.setAttribute("title", "ログイン");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <jsp:include page="head.jsp" />
    </head>

<body>
    <header></header>
    <main class="top">
        <h1><img src="${pageContext.request.contextPath}/images/ttl_tadaima.png" alt="ただいまをさがして"></h1>

        <div class="login_form">
            <!-- ログインフォーム -->
            <form action="login" method="post" class="mar_B5" target="content">
                <p>メールアドレス</p>
                <input type="text" name="email" required>

                <p>パスワード</p>
                <input type="password" name="password" required>

                <input type="submit" value="ログイン">
            </form>
            <!-- 新規登録フォーム -->
            <form action="register" method="get" target="content">
                <input type="submit" value="新規登録">
            </form>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/audioController.js"></script>
	<script>
        window.onload = function() {
            if (parent && parent.requestBGM) {
                parent.requestBGM('<%= request.getContextPath() %>/audio/index.mp3');
            }
        };
    </script>
</body>
</html>
