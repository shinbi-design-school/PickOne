package com.design_shinbi.searchinghome.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/naming")
public class NamingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/jsp/naming.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String catName = request.getParameter("catName");
		request.getSession().setAttribute("catName", catName);

		 String prologueText = "ポカポカ陽気に誘われて、森へお出かけした" +catName + "ちゃん。\r\n"
        		+ "蝶々と遊びたくて追いかけて…追いかけて…気がつけば、いつの間にか森の奥深くまで来てしまいました。\r\n"
        		+ "\r\n"
        		+ "「…あれ？ここ、どこ？」\r\n"
        		+ "遊び慣れた森なのに、いつもと違って見えて、迷子になってしまったみたい。\r\n"
        		+ "\r\n"
        		+ "「おかあさんに会いたいよ…お腹もすいてきたよ…」\r\n"
        		+ "\r\n"
        		+ "そんなとき、"+ catName + "ちゃんはふと、お母さんの言葉を思い出しました。\r\n"
        		+ "\r\n"
        		+ "「そうだ…お誕生日にもらったこの鈴…\r\n"
        		+ "“クイズに正解していけば、おうちまで導いてくれる”って、お母さん言ってたっけ…！」\r\n"
        		+ "\r\n"
        		+ "一人で心細いけど、大好きなお母さんに会いたいから――\r\n"
        		+ catName +"ちゃんは勇気を出して、クイズに挑むことを決めました。\r\n"
        		+ "";
		 request.getSession().setAttribute("prologueText", prologueText);
		 
		response.sendRedirect("prologue");
	}

}
