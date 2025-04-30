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

            <!-- エラーメッセージの表示 -->
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null && !error.isEmpty()) { %>
                <p class="error"><%= error %></p>
            <% } %>

            <!-- ログインフォーム -->
            <form action="login" method="post">
                <p>メールアドレス</p>
                <input type="text" name="email" required>

                <p>パスワード</p>
                <input type="password" name="password" required>

                <input type="submit" value="ログイン">
            </form>
            <!-- 新規登録フォーム -->
            <form action="register" method="get">
                <input type="submit" value="新規登録">
            </form>
        </div>
    </main>
    <footer></footer>
</body>
</html>
