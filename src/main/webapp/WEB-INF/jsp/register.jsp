<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー登録</title>
</head>
<body>
    <h1>ユーザー登録</h1>

    <form action="register" method="post">
        <p>
            ユーザー名：<input type="text" name="name" required>
        </p>
        <p>
            メールアドレス：<input type="email" name="email" required>
        </p>
        <p>
            パスワード：<input type="password" name="password" required>
        </p>
        <p>
            <button type="submit">登録する</button>
        </p>
    </form>
    <form action="login" method="get">
    <p><button type="submit">ログイン</button>
    </form>

    <p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
</body>
</html>
