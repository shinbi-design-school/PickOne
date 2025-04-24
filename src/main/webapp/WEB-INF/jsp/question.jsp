<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.Question" %>
<%@ page import="java.util.*" %>
<%
    Question question = (Question) request.getAttribute("question");
    String contextPath = request.getContextPath();

    Map<String, Integer> items = (Map<String, Integer>) session.getAttribute("items");
    if (items == null) {
        items = new HashMap<>();
        items.put("matatabi", 0);
        items.put("churu", 0);
    }

    Integer timeMultiplier = (Integer) session.getAttribute("timeMultiplier");
    if (timeMultiplier == null) {
        timeMultiplier = 1;
    }
    int baseTime = 10;
    int totalTime = baseTime * timeMultiplier;

    // タイムマルチプライヤーは使い切りなので、ここでリセット
    session.setAttribute("timeMultiplier", 1);
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>クイズ</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        #timer {
            font-size: 24px;
            color: red;
        }
        .choice { margin-bottom: 10px; }
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
<div id="timer">残り時間: <span id="time"><%= totalTime %></span> 秒</div>

<!-- アイテム使用ボタン -->
<div>
    <p>
        マタタビ: <%= items.get("matatabi") %> / チュール: <%= items.get("churu") %>
    </p>
    <button id="useMatatabi" <%= items.get("matatabi") <= 0 ? "disabled" : "" %>>マタタビを使う（選択肢-2）</button>
    <button id="useChuru" <%= items.get("churu") <= 0 ? "disabled" : "" %>>チュールを使う（時間2倍）</button>
</div>

<form id="quizForm">
    <div class="choice"><label><input type="radio" name="answer" value="1" required> <%= question.getChoice1() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="2"> <%= question.getChoice2() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="3"> <%= question.getChoice3() %></label></div>
    <div class="choice"><label><input type="radio" name="answer" value="4"> <%= question.getChoice4() %></label></div>
    <button type="submit">回答する</button>
</form>

<div id="overlay"></div>
<div id="resultModal"></div>

<script>
let timeLeft = <%= totalTime %>;
let timer = setInterval(function () {
    $('#time').text(timeLeft);
    if (timeLeft <= 0) {
        clearInterval(timer);

        // ここで選択されている値を取得（なければ null）
        let selectedAnswer = $('input[name="answer"]:checked').val() || null;

        $.ajax({
            type: 'POST',
            url: 'question',
            data: { answer: selectedAnswer },  // ここがポイント！
            success: function (data) {
                $('#resultModal').html(data);
                $('#overlay, #resultModal').fadeIn();
            },
            error: function () {
                alert("時間切れ後の解説取得に失敗しました。");
            }
        });
    }
    timeLeft--;
}, 1000);

    const correctAnswer = <%= question.getCorrectChoice() %>;

    // 回答送信時
    $('#quizForm').on('submit', function(e) {
        e.preventDefault();
        clearInterval(timer); // タイマー停止

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

    // 次の問題へ
    $(document).on('click', '#nextQuestionBtn', function() {
        window.location.href = 'question?proceed=true';
    });

    $('#overlay').on('click', function() {
    	e.preventDefault();
        e.stopPropagation();
    });

    // マタタビ使用
    $('#useMatatabi').on('click', function() {
        let wrongChoices = [1, 2, 3, 4].filter(n => n !== correctAnswer);
        let toHide = wrongChoices.sort(() => Math.random() - 0.5).slice(0, 2);
        toHide.forEach(i => {
            $('input[value="' + i + '"]').closest('.choice').hide();
        });

        $(this).prop('disabled', true);
        $.post('item', { item: 'matatabi' });
    });

    // チュール使用 → 再読み込みでtime2倍
    $('#useChuru').on('click', function() {
        $(this).prop('disabled', true);
        $.post('item', { item: 'churu' }, function() {
            // 時間だけ再設定（ページリロードしない）
            clearInterval(timer);
            timeLeft *= 2;
            timer = setInterval(function() {
                $('#time').text(timeLeft);
                if (timeLeft <= 0) {
                    clearInterval(timer);
                    alert("時間切れにゃ！");
                    $('#quizForm button[type="submit"]').prop('disabled', true);
                }
                timeLeft--;
            }, 1000);
        });
    });
</script>

</body>
</html>
