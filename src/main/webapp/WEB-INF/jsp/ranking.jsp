<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("title", "ランキング");
    List<Score> ranking = (List<Score>) request.getAttribute("ranking");
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top result">
        <h1><img src="${pageContext.request.contextPath}/images/rank_ttl.png"></h1>
        <div class="tbl_wrap">
        <%
        if(ranking == null || ranking.isEmpty()){
    %>
        <p>ランキングがありません。</p>
    <%
        } else {
    %>
            <table class="tbl">
                <tr>
                    <th>#</th>
                    <th>スコア</th>
                    <th>プレイ日時</th>
                </tr>
                <%
                int rank = 1;
                for(Score score : ranking) {
            %>
                <tr>
                    <td><%= rank++ %></td>
                    <td><%= score.getScore() %></td>
                    <td><%= score.getPlayedAt() %></td>
                </tr>
            <%
                }
            %>
            </table>
            <%
        }
    %>
            <div class="flex_box nav_area btn_nav">
                <a href="${pageContext.request.contextPath}/top">
                    メニューへ戻る</a>
                </a>
            </div>
    </main>
    <footer></footer>
</body>
</html>

