<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head><title>あそびかた</title></head>
<body>
    <h2>あそびかた</h2>
    <p>迷子の猫をおうちに返してあげよう<br>

	全15問のクイズにチャレンジ！<br>
	10問以上正解すると、猫ちゃんは無事におうちへ帰ることができるよ。<br>
	
	さらに…<br>
	4問連続で正解すると、冒険の役に立つアイテムがランダムでもらえる！<br>
	
	もらえるアイテム<br>
	
	マタタビ：選択肢が半分に減る！（2択になるよ）<br>
	
	チュール：制限時間がちょっと延びる！<br>
	
	準備はいい？<br>
	さあ、猫ちゃんと一緒にクイズの冒険へ出発しよう！</p>
    <form action="question" method="post" target="content">
        <button type="submit">ゲームスタート</button>
    </form>
    <form action="top" method="post" target="content">
        <button type="submit">メニューへ</button>
    </form>
    <script src="${pageContext.request.contextPath}/js/audioController.js"></script>
	<script>
        window.onload = function() {
            if (parent && parent.requestBGM) {
                parent.requestBGM('<%= request.getContextPath() %>/audio/index.mp3');
            }
        };
    </script>
</body>
</html>