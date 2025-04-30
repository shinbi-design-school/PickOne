<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Score" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("title", "プレイ履歴");
    List<Score> history = (List<Score>) request.getAttribute("history");
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top result">
        <h1><img src="${pageContext.request.contextPath}/images/his_ttl.png"></h1>
        <div class="tbl_wrap">
        <%
        if(history == null || history.isEmpty()){
    %>
        <p>履歴がありません。</p>
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