/*let timer;
let itemUsed = false;

// アイテム使用処理


$('#churuUsage').click(function() {
    if (itemUsed) return;
    if (items.churu > 0) {
        itemUsed = true;
        $('#churuUsage').text('× ' + (items.churu - 1)); // 所持数を更新
        totalTime *= 2; // チュール使用で時間倍増
        startTimer(); // タイマー再スタート
    }
});

function startTimer() {
    timer = setInterval(function() {
        if (totalTime <= 0) {
            clearInterval(timer);
            $('#timeRemaining').text('0');
            // 不正解の処理
            alert('制限時間が切れました。不正解です');
            window.location.href = 'question?next=true'; // 次の問題へ
        } else {
            $('#timeRemaining').text(totalTime);
            totalTime--;
        }
    }, 1000);
}

// 初回のタイマー開始
startTimer();
*/