<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%
    Question question = (Question) request.getAttribute("question");
%>
<head>
    <jsp:include page="head.jsp" />
</head>
<p class="red">正解は <%= question.getCorrectChoice() %> 番！</p>
<p class="ans_pht"><img src="<%= request.getContextPath() + "/" + question.getImageUrl()%>"></p>
<p class="kaisetsu"><%= question.getExplanation() %></p>
<div class="right">
    <a href="<%= request.getContextPath() %>/question?next=true" class="go-next">
        <img src="<%= request.getContextPath() %>/images/btn_nextq.png" class="btn_next">
    </a>
</div>
