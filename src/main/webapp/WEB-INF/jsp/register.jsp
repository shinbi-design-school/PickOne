<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("pageTitle", "ただいまをさがして");
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top">
        <h1><img src="${pageContext.request.contextPath}/images/ttl_tadaima.png" alt="ただいまをさがして"></h1>
        
        <div class="login_form">
            <form action="register" method="post">
	            <p>名前</p>
	            <input type="text" name="name">
	            <p>メールアドレス</p>
	            <input type="text" name="email">
	            <p>パスワード</p>
	            <input type="text" name="password">
	            <input type="submit" value="登録">
	        </form>
	        <form action="login" method="get">
	            <input type="submit" value="ログイン">
            </form>
        </div>
        </main>
</body>
</html>
