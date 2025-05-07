<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
     String contextPath = request.getContextPath();
     request.setAttribute("title", "ユーザー登録");
     String error = (String)request.getAttribute("error");
 %>
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
            <form action="register" method="post">
                <p class="attention">※ <%= error %></p>
                <p>名前</p>
                <input type="text" name="name" required>
                <p>メールアドレス</p>
                <input type="text" name="email" required>
                <p>パスワード</p>
                <input type="password" name="password" required>
                <div class="flex_box">
                <input type="submit" value="登録">
            </form>
            <form action="login" method="get">
				<input type="submit" value="ログイン">
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