<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String catName = (String) session.getAttribute("catName");
    String prologueText = (String) session.getAttribute("prologueText");
%>

<html>
<head><title>プロローグ</title></head>
<body>
    <h2>プロローグ</h2>
    <p><%= prologueText %></p>
    <form action="question" method="post">
        <button type="submit">ゲームスタート</button>
    </form>
</body>
</html>