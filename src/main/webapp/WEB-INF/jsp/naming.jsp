<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("title", "名付け");
%>

<!DOCTYPE html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <header></header>
    <main class="top cat_form">
        <h1><img src="${pageContext.request.contextPath}/images/ttl_tadaima.png"></h1>
        <form action="naming" method="post" target="content">
            <p>この子に名前を付けてください。</p>
            <input type="text" name="catName" required>
            <input type="submit" value="次へ">
        </form>
        <img src="${pageContext.request.contextPath}/images/cat_nobi.png" class="cat_img">
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
