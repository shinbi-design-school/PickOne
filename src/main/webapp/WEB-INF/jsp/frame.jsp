<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="head.jsp" />
    <style>
    html, body {
        margin: 0;
        padding: 0;
        overflow: hidden; /* ← スクロールを隠す */
        height: 100%;
    }
</style>

</head>
<body>

    <!-- BGMを再生し続けるiframe -->
<iframe id="bgmFrame"
        src="${pageContext.request.contextPath}/pages/bgm_player.jsp"
        style="display: none;"
        allow="autoplay"
        onload="requestBGM('<%= request.getContextPath() %>/audio/index.mp3');"></iframe>
    <!-- コンテンツ表示用iframe -->
    <iframe id="contentFrame"
            src="register"
            name="content"
            style="width: 100%; height: 100vh; border: none;"></iframe>

    <!-- 音声制御用スクリプト -->
    <script src="js/audioController.js"></script>
    <script>
        window.requestBGM = function(src) {
            const frame = document.getElementById("bgmFrame");
            if (frame && frame.contentWindow) {
                frame.contentWindow.postMessage({ type: "PLAY_BGM", src: src }, "*");
            }
        };

        window.onload = function() {
            requestBGM('audio/index.mp3');
        };
    </script>

</body>
</html>
