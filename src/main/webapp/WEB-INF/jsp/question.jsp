<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%@ page import="java.util.*" %>
<%
    Question question = (Question) request.getAttribute("question");
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>クイズ</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        #resultModal {
            display: none;
            position: fixed;
            top: 20%;
            left: 30%;
            width: 40%;
            padding: 20px;
            background: #fff;
            border: 2px solid #333;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.4);
        }
        #overlay {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
</head>
<body>

<h2 id="questionText"><%= question.getQuestionText() %></h2>

<form id="quizForm">
    <input type="radio" name="answer" value="1" required> <%= question.getChoice1() %><br>
    <input type="radio" name="answer" value="2"> <%= question.getChoice2() %><br>
    <input type="radio" name="answer" value="3"> <%= question.getChoice3() %><br>
    <input type="radio" name="answer" value="4"> <%= question.getChoice4() %><br>
    <button type="submit">回答する</button>
</form>

<div id="overlay"></div>
<div id="resultModal"></div>

<script>
    $(function() {
        $('#quizForm').on('submit', function(e) {
            e.preventDefault();
            let answer = $('input[name="answer"]:checked').val();

            $.ajax({
                type: 'POST',
                url: 'question',
                data: { answer: answer },
                success: function(data) {
                    $('#resultModal').html(data);
                    $('#overlay, #resultModal').fadeIn();
                }
            });
        });

        // 「次の問題へ」クリックで再読み込み
        $(document).on('click', '#nextQuestionBtn', function() {
            window.location.href = 'question?proceed=true';
        });

        // オーバーレイクリックでモーダル閉じる（任意）
        $('#overlay').on('click', function() {
            $('#overlay, #resultModal').fadeOut();
        });
    });
</script>

</body>
</html>
