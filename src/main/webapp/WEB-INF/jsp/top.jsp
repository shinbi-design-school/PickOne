<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.design_shinbi.searchinghome.model.entity.User" %>
<%
    User user = (User) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>トップページ</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f0f8ff;
            padding-top: 50px;
        }
        .container {
            width: 400px;
            margin: 0 auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px #ccc;
        }
        h2 {
            text-align: center;
        }
        .player-name {
            margin-top: 20px;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
        }
        .btn {
            margin-top: 20px;
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .user-info {
            margin-bottom: 20px;
            font-size: 14px;
            color: #333;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>クイズゲームへようこそ！</h2>

        <div class="user-info">
            ログイン中：<strong><%= user.getName() %></strong>
        </div>

        <!-- クイズ開始フォーム -->
        <form action="question" method="post">
            <div class="player-name">
                <label for="catName">猫の名前を入力してください：</label>
                <input type="text" id="catName" name="catName" placeholder="例：しんび" required>
            </div>
            <input type="submit" class="btn" value="クイズを始める">
        </form>

        <!-- ランキング表示ボタン -->
        <form action="ranking" method="get">
            <input type="submit" class="btn" value="ランキングを見る">
        </form>

        <!-- 履歴表示ボタン -->
        <form action="history" method="get">
            <input type="submit" class="btn" value="スコア履歴を見る">
        </form>

        <!-- ログアウトボタン -->
        <form action="logout" method="post">
            <input type="submit" class="btn" style="background-color: #f44336;" value="ログアウト">
        </form>
    </div>

</body>
</html>
