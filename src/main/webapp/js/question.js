$(function () {
    const basePath = location.pathname.split('/')[1] ? '/' + location.pathname.split('/')[1] : '';
    let timeLeft = parseInt($('.time_box p').text().trim());
    let timer;
    let usedItemThisQuestion = false;

    startTimer();

    function startTimer() {
        timer = setInterval(() => {
            timeLeft--;
            updateTimerDisplay();

            if (timeLeft <= 0) {
                clearInterval(timer);
                $('#answerInput').val(""); // 未選択として扱う
                $('.modal-container').addClass('active');
            }
        }, 1000);
    }

    function updateTimerDisplay() {
        $('.time_box p').text(timeLeft);
    }

    function stopTimer() {
        clearInterval(timer);
    }

    function extendTimer() {
        timeLeft *= 2;
        updateTimerDisplay();
    }

	function reduceChoicesToTwo() {
	    let correct = window.correctChoice;
	    let $choices = $('.a_box');
	    let incorrects = $choices.filter((_, el) => $(el).data('answer') != correct);
	    let shuffled = incorrects.sort(() => 0.5 - Math.random());
	    shuffled.slice(0, 2).hide();
	}

    function useItem(item) {
        $.ajax({
            url: basePath + "/item",
            type: "POST",
            data: { item: item },
            success: function () {
                const $itemBox = item === 'churu' ? $('#churuUsage') : $('#matatabiUsage');
                let countText = $itemBox.text();
                let count = parseInt(countText.match(/\d+/));
                $itemBox.html($itemBox.html().replace(/×\s*\d+/, '× ' + (count - 1)));

                if (item === 'churu') {
                    extendTimer();
                } else if (item === 'matatabi') {
                    reduceChoicesToTwo();
                }

                usedItemThisQuestion = true;
            },
            error: function () {
                alert('アイテム使用に失敗しました');
            }
        });
    }

    $('#churuUsage').on('click', function (e) {
        e.preventDefault();
        if (!usedItemThisQuestion) useItem('churu');
    });

    $('#matatabiUsage').on('click', function (e) {
        e.preventDefault();
        if (!usedItemThisQuestion) useItem('matatabi');
    });

    $('.a_box').on('click', function (e) {
        e.preventDefault();
        stopTimer();
        let selectedAnswer = $(this).data('answer');
        $('#answerInput').val(selectedAnswer);
        $('.modal-container').addClass('active');
    });

    $('.modal-close').on('click', function () {
        $('.modal-container').removeClass('active');
    });

    $('.go-next').on('click', function (e) {
        e.preventDefault();
        $('#answerForm').submit();
    });
});

