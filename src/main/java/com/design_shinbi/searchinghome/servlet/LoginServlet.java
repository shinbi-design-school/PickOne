package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.design_shinbi.searchinghome.model.Const;
import com.design_shinbi.searchinghome.model.dao.UserDAO;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ログインページの表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            Connection connection = DBUtil.connect();
            String jsp = null;
            String error = "";
            User user = null;

            UserDAO userDao = new UserDAO(connection);

            String email = request.getParameter("email");
            if (email == null || email.isEmpty()) {
                error = "メールアドレスを入力してください。";
            }

            String password = request.getParameter("password");
            if (password == null || password.isEmpty()) {
                error = error + "パスワードを入力してください。";
            }

            if (error.isEmpty()) {
                user = userDao.login(email, password);
            }

            if (user == null) {
                if (error.isEmpty()) {
                    error = "ユーザー名もしくはパスワードが違います。";
                }
                request.setAttribute("error", error);
                jsp = "/WEB-INF/jsp/login.jsp";
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(Const.LOGIN_USER_KEY, user);
                jsp = "/WEB-INF/jsp/top.jsp";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
