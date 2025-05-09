package com.design_shinbi.searchinghome.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DB_NAME = "searchinghome";
	private static final String TEST_DB_NAME = "searchinghome_test";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	public static Connection connect() throws ClassNotFoundException, SQLException{
		return connect(false);
	}
	
	public static Connection connect(boolean isTest) throws ClassNotFoundException, SQLException{
		String dbName = isTest ? TEST_DB_NAME : DB_NAME;
		String url = "jdbc:mysql://localhost/" + dbName;
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
		return con;
	}
}
