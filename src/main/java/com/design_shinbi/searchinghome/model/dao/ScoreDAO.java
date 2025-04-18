package com.design_shinbi.searchinghome.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.design_shinbi.searchinghome.model.entity.Score;

public class ScoreDAO {
	protected Connection connection;
	
	public ScoreDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void save(Score score) throws SQLException{
		String sql = "INSERT INTO scores(user_id, score, played_at)"
				+ " VALUES(?, ?, ?)";
		
		System.out.println("Saving score for userId=" + score.getUserId());
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		
		statement.setInt(1, score.getUserId());
		statement.setInt(2, score.getScore());
		statement.setTimestamp(3, Timestamp.valueOf(score.getPlayedAt()));
		
		statement.executeUpdate();
		statement.close();
	}
	
	public List<Score> findByUserId(int userId) throws SQLException{
		List<Score> list = new ArrayList<Score>();
		
		String sql = "SELECT * FROM scores WHERE user_id = ? "
				+ "ORDER BY played_at DESC";
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, userId);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Score score = new Score(
					rs.getInt("id"),
					rs.getInt("user_id"),
					rs.getInt("score"),
					rs.getTimestamp("played_at").toLocalDateTime()
					);
			list.add(score);
		}
		
		return list;
	}
	
	public List<Score> findTopScore(int limit) throws SQLException{
		List<Score> list = new ArrayList<Score>();
		
		String sql = "SELECT * FROM scores ORDER BY score DESC, played_at ASC LIMIT ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, limit);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Score score = new Score(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("score"),
				rs.getTimestamp("played_at").toLocalDateTime()
				);
			list.add(score);
		}
		return list;
	}
}
