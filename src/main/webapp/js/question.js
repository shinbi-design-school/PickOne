$(function () {
    // 選択肢をクリックしたらPOST送信
    $(document).on('click', '.a_box', function (e) {
        e.preventDefault(); // aタグのデフォルト動作（遷移）を無効化

        const selectedAnswer = $(this).data('answer');

        // 過去の hidden input があれば削除
        $('#answerForm input[name="answer"]').remove();

        // hidden input を追加してPOST
        const input = $('<input>', {
            type: 'hidden',
            name: 'answer',
            value: selectedAnswer
        });

        $('#answerForm').append(input);
        $('#answerForm').submit();  // 明示的に送信
    });
});


    // 次の問題へ（GETで遷移）
    $(document).on('click', '.extra-close', function (e) {
        e.preventDefault();
        window.location.href = 'question?next=true';
    });

    // マタタビ・チュールなど（任意機能）
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
