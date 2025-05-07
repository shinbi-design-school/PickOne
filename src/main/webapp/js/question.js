$(function () {
    let timeLeft = parseInt($('.time_box p').text().trim());
    let timer;
    let usedItemThisQuestion = false;

    startTimer();

	function startTimer() {
	    timeLeft = baseTime * timeMultiplier;  // 初期状態に戻す
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

    function playEffect(src) {
        const audio = new Audio(src);
        audio.volume = 0.7;
        audio.play().catch(e => console.warn("効果音エラー:", e));
    }

    function useItem(item) {
        $.ajax({
            url: contextPath + "/item",
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

        if (usedItemThisQuestion) return;

        let countText = $(this).text();
        let count = parseInt(countText.match(/\d+/));
        if (count <= 0) return;

        useItem('churu');
    });

    $('#matatabiUsage').on('click', function (e) {
        e.preventDefault();

        if (usedItemThisQuestion) return;

        let countText = $(this).text();
        let count = parseInt(countText.match(/\d+/));
        if (count <= 0) return;

        useItem('matatabi');
    });

    $('.a_box').on('click', function (e) {
        e.preventDefault();
        stopTimer();
        let selectedAnswer = $(this).data('answer');
        $('#answerInput').val(selectedAnswer);
        $('.modal-container').addClass('active');

        // 正解・不正解に応じて効果音を再生
        if (selectedAnswer === window.correctChoice) {
            playEffect(contextPath + "/audio/correct.mp3"); // 正解音
        } else {
            playEffect(contextPath + "/audio/wrong.mp3"); // 不正解音
        }
    });

    $('.modal-close').on('click', function () {
        $('.modal-container').removeClass('active');
    });

    $('.go-next').on('click', function (e) {
        e.preventDefault();
        playEffect(contextPath + "/audio/next.mp3"); // 次の問題へ音
		setTimeout(function () {
		       $('#answerForm').submit();
		   }, 300); 
    });
});

