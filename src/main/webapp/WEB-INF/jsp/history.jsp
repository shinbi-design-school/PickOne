<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%
    List<Score> history = (List<Score>) request.getAttribute("history");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>スコア履歴</title>
</head>
<body>
    <h1>あなたのスコア履歴</h1>

    <table border="1">
        <tr>
            <th>#</th>
            <th>スコア</th>
            <th>プレイ日時</th>
        </tr>
        <%
            int count = 1;
            for(Score score : history) {
        %>
            <tr>
                <td><%= count++ %></td>
                <td><%= score.getScore() %></td>
                <td><%= score.getPlayedAt() %></td>
            </tr>
        <%
            }
        %>
    </table>

    <p><a href="index.jsp">トップへ戻る</a></p>
</body>
</html>
