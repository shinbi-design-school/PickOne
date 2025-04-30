$(function () {
    let timeLeft = parseInt($('#time').data('time'), 10);
    let correctAnswer = parseInt($('#questionText').data('correct'), 10);
    let timer = startTimer(timeLeft, correctAnswer);

    $(document).on('click', '.a_box', function (e) {
        e.preventDefault();  // デフォルトのリンク動作を防ぐ
        let selectedAnswer = $(this).data('answer');  // data-answer属性で選択肢を取得
        clearInterval(timer);
        submitAnswer(selectedAnswer);  // サーバーに選択肢を送信
    });
	
	$('.extra-close').on('click', function(e) {
	        e.preventDefault();  // デフォルトのリンク動作（ページ遷移）を防ぐ

	        // 必要なデータを取得（ここでは単純に data-next 属性を使う）
	        let nextQuestion = $(this).data('next');

	        // POSTリクエストを送信
	        $.ajax({
	            url: 'question',  // サーブレットのURL
	            method: 'POST',  // POSTリクエストを送る
	            data: { next: nextQuestion },  // 送信するデータ
	            success: function(response) {
	                // サーバーからのレスポンスを処理（次の質問を表示するなど）
	                console.log('次の質問が読み込まれました');
	                // 例: 次の質問に遷移
	                window.location.href = 'question?proceed=true';
	            },
	            error: function() {
	                alert('次の質問の取得に失敗しました。');
	            }
	        });
	    });
	});
	
    $(document).on('click', '#nextQuestionBtn', function () {
        window.location.href = 'question?proceed=true';
    });

    $('#overlay').on('click', function (e) {
        e.preventDefault();
        e.stopPropagation();
    });

    $('#useMatatabi').on('click', function () {
        let wrongChoices = [1, 2, 3, 4].filter(n => n !== correctAnswer);
        let toHide = wrongChoices.sort(() => Math.random() - 0.5).slice(0, 2);
        toHide.forEach(i => {
            $('input[value="' + i + '"]').closest('.choice').hide();
        });

        $(this).prop('disabled', true);
        $.post('item', { item: 'matatabi' });
    });

    $('#useChuru').on('click', function () {
        $(this).prop('disabled', true);
        $.post('item', { item: 'churu' }, function () {
            clearInterval(timer);
            timeLeft *= 2;
            timer = startTimer(timeLeft, correctAnswer);
        });
    });


function startTimer(duration, correctAnswer) {
    let timeLeft = duration;
    let timer = setInterval(function () {
        $('#time').text(timeLeft);
        if (timeLeft <= 0) {
            clearInterval(timer);
            let selectedAnswer = $('input[name="answer"]:checked').val() || null;
            submitAnswer(selectedAnswer);
        }
        timeLeft--;
    }, 1000);
    return timer;
}

function submitAnswer(answer) {
    $.ajax({
        type: 'POST',
        url: 'question',
        data: { answer: answer },
        success: function (data) {
            $('#resultModal').html(data);             // 解説をモーダル内に挿入
            $('.modal-container').addClass('active'); // モーダルを表示
        },
        error: function () {
            alert("解説の取得に失敗しました。");
        }
    });
}
