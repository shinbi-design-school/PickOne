let bgmAudio = null;

function playBGM(bgmPath) {
    stopBGM();
    bgmAudio = new Audio(bgmPath);
    bgmAudio.loop = true;
    bgmAudio.volume = 0.5;
    bgmAudio.play();
}

function stopBGM() {
    if (bgmAudio) {
        bgmAudio.pause();
        bgmAudio.currentTime = 0;
        bgmAudio = null;
    }
}

function playEffect(effectPath) {
    const sfx = new Audio(effectPath);
    sfx.play();
}
