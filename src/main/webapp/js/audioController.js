/**
 * BGMを再生するために bgm_player.jsp にメッセージを送る
 * @param {string} src - 再生したいBGMファイルのパス（例: 'audio/index.mp3'）
 */
function requestBGM(src) {
    const frame = parent.document.getElementById("bgmFrame");
    if (frame && frame.contentWindow) {
        frame.contentWindow.postMessage({ type: "PLAY_BGM", src: src }, "*");
    } else {
        console.warn("BGMフレームが見つかりません");
    }
}

/**
 * 効果音を再生する（BGMとは別）
 * @param {string} src - 効果音ファイルのパス
 */
function playEffect(src) {
    const audio = new Audio(src);
    audio.volume = 0.7;
    audio.play().catch(e => console.warn("効果音エラー:", e));
}

// ユーザーの操作を待つ
/*
document.body.addEventListener('click', function() {
    if (audio) {
        audio.play().catch(err => {
            console.warn("BGMの再生に失敗:", err);
        });
    }
});
*/