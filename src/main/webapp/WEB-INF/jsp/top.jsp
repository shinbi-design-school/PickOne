<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.User" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("pageTitle", "ただいまをさがして");
    User user = (User) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
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
  

        <div class="user-info">
            ログイン中：<strong><%= user.getName() %></strong>
        </div>

        <!-- クイズ開始フォーム -->
        <form action="naming" method="get">
            <input type="submit" class="btn" value="クイズを始める">
        </form>

        <!-- ランキング表示ボタン -->
        <form action="ranking" method="get">
            <input type="submit" class="btn" value="ランキングを見る">
        </form>

        <!-- 履歴表示ボタン -->
        <form action="history" method="get">
            <input type="submit" class="btn" value="スコア履歴を見る">
        </form>
        
        <form action="how" method="get">
            <input type="submit" class="btn" value="あそびかたを見る">
        </form>

        <!-- ログアウトボタン -->
        <form action="logout" method="get">
            <input type="submit" class="btn" style="background-color: #f44336;" value="ログアウト">
        </form>
    </div>

</body>
</html>
