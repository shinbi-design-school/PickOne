$(function () {
    let timeLeft = parseInt($('#time').data('time'), 10);
    let correctAnswer = parseInt($('#questionText').data('correct'), 10);
    let timer = startTimer(timeLeft, correctAnswer);

    $('#quizForm').on('submit', function (e) {
        e.preventDefault();
        clearInterval(timer);
        let answer = $('input[name="answer"]:checked').val();
        submitAnswer(answer);
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


