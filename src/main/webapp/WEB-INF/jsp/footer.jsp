<footer></footer>

<!-- 非表示の iframe（BGM 再生用） -->
<iframe id="bgmFrame"
        src="${pageContext.request.contextPath}/pages/bgm_player.jsp"
        style="display:none;"
        allow="autoplay"></iframe>

<script src="${pageContext.request.contextPath}/js/audioController.js"></script>

<script>
function requestBGM(src) {
    const frame = document.getElementById("bgmFrame");
    if (frame && frame.contentWindow) {
        frame.contentWindow.postMessage({ type: "PLAY_BGM", src: src }, "*");
    }
}
</script>

