<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String contextPath = request.getContextPath();
	request.setAttribute("title", "ユーザー登録");
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
	<header></header>
	<main class="top">
		<h1>
			<img src="${pageContext.request.contextPath}/images/ttl_tadaima.png"
				alt="ただいまをさがして">
		</h1>
		<div class="login_form">
			<form action="register" method="post">
				<p>名前</p>
				<input type="text" name="name" required>
				<p>メールアドレス</p>
				<input type="text" name="email" required>
				<p>パスワード</p>
				<input type="password" name="password" required>
				<input type="submit" value="登録">
			</form>
			<form action="login" method="get">
				<input type="submit" value="ログイン">
			</form>
		</div>
	</main>
</body>
</html>
