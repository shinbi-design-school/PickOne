<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%
    List<Score> ranking = (List<Score>) request.getAttribute("ranking");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ランキング</title>
</head>
<body>
    <h1>ランキング（上位10位）</h1>

    <table border="1">
        <tr>
            <th>順位</th>
            <th>ユーザーID</th>
            <th>スコア</th>
            <th>プレイ日時</th>
        </tr>
        <%
            int rank = 1;
            for(Score score : ranking) {
        %>
            <tr>
                <td><%= rank++ %></td>
                <td><%= score.getUserId() %></td>
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
