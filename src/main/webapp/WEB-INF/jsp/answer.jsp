<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%@ page import="java.util.*" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    Question question = (Question) request.getAttribute("question");
    Integer selected = (Integer) request.getAttribute("selected");
    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    Boolean isLastQuestion = (Boolean) request.getAttribute("isLastQuestion");
    if (isLastQuestion == null) isLastQuestion = false;
    String contextPath = request.getContextPath();
    String nextButtonImage = isLastQuestion
        ? "<img src='" + contextPath + "/images/btn_next.png' class='btn_next'>"
        : "<img src='" + contextPath + "/images/btn_nextq.png' class='btn_next'>";
%>
<html>
<head>
    <jsp:include page="head.jsp" />
</head>
<body>
    <main class="top quiz flex_box">
        <!-- 以前のモーダル部をそのまま常時表示に -->
        <div class="modal-container active">
            <div class="modal-body">
                <div class="modal-close">×</div>
                <div class="modal-content">
                    <p class="red">正解は<%= question.getCorrectChoice() %>番！</p>
                    <p class="ans_pht">
                        <img src="<%= contextPath + "/" + question.getImageUrl() %>">
                    </p>
                    <p class="kaisetsu"><%= question.getExplanation() %></p>
                    <div class="right">
                        <a href="#" class="extra-close" data-next="true">
                            <%= nextButtonImage %>
                        </a>
                    </div>
                    <img src="${pageContext.request.contextPath}/images/cat_ans.png" class="cat_img03">
                </div>
            </div>
        </div>
    </main>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/modal.js"></script>
    <script>
        $(function () {
            // 次の問題へ遷移
            $(document).on('click', '.extra-close', function(e) {
                e.preventDefault();
                $.post('question', { next: true }, function () {
                    window.location.href = 'question';
                }).fail(function () {
                    alert('次の問題の取得に失敗しました。');
                });
            });
        });
    </script>
</body>
</html>