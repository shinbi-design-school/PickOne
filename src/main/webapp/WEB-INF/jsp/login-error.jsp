<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top">
        <h1><img src="${pageContext.request.contextPath}/images/ttl_tadaima.png"></h1>
        <div class="login_form">
            <form action="login" method="post" target="content">
                <p class="attention">※ ログインエラーです。もう一度入力してください。</p>
                <p>メールアドレス</p>
                <input type="text" name="email">
                <p>パスワード</p>
                <input type="password" name="password">
                <input type="submit" value="ログイン">
            </form>
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