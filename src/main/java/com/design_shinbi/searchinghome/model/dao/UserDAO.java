package com.design_shinbi.searchinghome.model.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.design_shinbi.searchinghome.model.Const;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.PasswordUtil;


public class UserDAO {
	protected Connection connection;
	
	public UserDAO(Connection connection) throws NoSuchAlgorithmException, SQLException{
		this.connection = connection;
		this.init();
	}
	
	public void init() throws SQLException, NoSuchAlgorithmException{
		if(this.count() == 0) {
			this.add(Const.DEFAULT_USER_NAME,
					Const.DEFAULT_USER_EMAIL,
					Const.DEFAULT_USER_PASSWORD,
					true);
		}
	}	

	
	public User add(String name, String email, String password, boolean admin)
	        throws NoSuchAlgorithmException, SQLException {
	    String salt = PasswordUtil.generateSalt();
	    String hashedPassword = PasswordUtil.hashPasswordWithSaltPepper(password, salt);
	    Timestamp now = new Timestamp(System.currentTimeMillis());
	    
	    // INSERT文
	    String sql = "INSERT INTO users(name, email, password, salt, "
	            + "admin, created_at, updated_at) "
	            + "values(?, ?, ?, ?, ?, ?, ?)";
	    
	    // PreparedStatementの作成（自動生成されたキーを取得するための設定）
	    PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    
	    statement.setString(1, name);
	    statement.setString(2, email);
	    statement.setString(3, hashedPassword);
	    statement.setString(4, salt);
	    statement.setBoolean(5, admin);
	    statement.setTimestamp(6, now);
	    statement.setTimestamp(7, now);
	    
	    // INSERT文の実行
	    statement.executeUpdate();
	    
	    // 生成されたキー（ID）を取得
	    ResultSet rs = statement.getGeneratedKeys();
	    
	    if (rs.next()) {
	        // IDを取得
	        int userId = rs.getInt(1);  // 1は最初のカラム（生成されたID）
	        
	        // Userオブジェクトを作成してIDをセット
	        User user = new User();
	        user.setId(userId);
	        user.setName(name);
	        user.setEmail(email);
	        user.setPassword(hashedPassword);
	        user.setAdmin(admin);
	        
	        // ステートメントをクローズ
	        statement.close();
	        
	        // 登録したユーザーを返す
	        return user;
	    }
	    
	    // IDが取得できなかった場合のエラー
	    throw new SQLException("ユーザーIDの取得に失敗しました。");
	}

		
	
	public void update(User user) throws SQLException{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		String sql = "UPDATE users SET name = ?, email = ?, admin = ?, "
				+ "updated_at = ? WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		statement.setBoolean(3, user.isAdmin());
		statement.setTimestamp(4, now);
		statement.setInt(5, user.getId());
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(int id) throws SQLException{
		String sql = "DELETE FROM users WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);	
		statement.setInt(1, id);
		statement.executeUpdate();
		statement.close();
	}
	
	public User createUser(ResultSet rs) throws SQLException{
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setSalt(rs.getString("salt"));
		user.setAdmin(rs.getBoolean("admin"));
		user.setCreatedAt(rs.getTimestamp("created_at"));
		user.setUpdatedAt(rs.getTimestamp("updated_at"));
		return user;
		
	}
	
	public List<User> findAll() throws SQLException{
		List<User> list = new ArrayList<User>();
		String sql = "SELECT * FROM users";
		Statement statement = this.connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			User user = createUser(rs);
			list.add(user);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public User find(int id) throws SQLException{
		User user = null;
		String sql = "SELECT * FROM users WHERE id = ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		if(rs.next()) {
			user = createUser(rs);
		}
		rs.close();
		statement.close();
		return user;
	}
	
	public User findByEmail(String email) throws SQLException{
		User user = null;
		String sql = "SELECT * FROM users WHERE email = ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();
		if(rs.next()) {
			user = createUser(rs);
		}
		rs.close();
		statement.close();
		return user;
	}
	
	public int count() throws SQLException{
		int count = 0;
		String sql = "SELECT COUNT(*) AS count FROM users";
		Statement statement = this.connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if(rs.next()) {
			count = rs.getInt("count");
		}
		rs.close();
		statement.close();
		return count;
	}
	
	public User login(String email, String password)
		throws SQLException, NoSuchAlgorithmException{
			User user = this.findByEmail(email);
			
			if(user != null) {
				String hashedPasswordFromDB = user.getPassword();
				String saltFromDB = user.getSalt();
				String hashedInputPassword = PasswordUtil.hashPasswordWithSaltPepper(password, saltFromDB);
				if(!hashedPasswordFromDB.equals(hashedInputPassword)) {
					user = null;
				}
			}
			return user;
	}

}
