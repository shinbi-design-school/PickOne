<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>BGM Player</title>
</head>
<body>
<script>
    let audio = null;

    window.addEventListener("message", function(event) {
        const data = event.data;

        if (!data || data.type !== "PLAY_BGM" || !data.src) {
            return;
        }

        // 同じ音楽なら再再生しない
        if (audio && audio.src === new URL(data.src, location.origin).href) {
            return;
        }

        // 既存のBGMを停止
        if (audio) {
            audio.pause();
            audio = null;
        }

        // 新しいBGMを再生
        audio = new Audio(data.src);
        audio.loop = true;
        audio.volume = 0.5;

        // ユーザー操作が必要な場合のエラーを無視
        audio.play().catch(err => {
            console.warn("BGMの再生に失敗:", err);
        });
    });
</script>
</body>
</html>
