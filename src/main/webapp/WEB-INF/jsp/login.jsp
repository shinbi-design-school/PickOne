<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f5f5f5;
            padding-top: 50px;
        }
        .login-form {
            background-color: #fff;
            width: 350px;
            margin: 0 auto;
            padding: 30px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        h2 {
            text-align: center;
        }
        label {
            margin-top: 10px;
            display: block;
        }
        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            box-sizing: border-box;
        }
        .error {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #2196F3;
            border: none;
            color: white;
            margin-top: 20px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>

    <div class="login-form">
        <h2>ログイン</h2>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null && !error.isEmpty()) { %>
            <p class="error"><%= error %></p>
        <% } %>

        <form action="login" method="post">
            <label for="email">メールアドレス:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">パスワード:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="ログイン">
        </form>
        <form action="register" method="get">
            <input type="submit" value="新規登録">
        </form>
    </div>

</body>
</html>
