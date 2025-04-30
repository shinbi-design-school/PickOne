package com.design_shinbi.searchinghome.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.design_shinbi.searchinghome.model.entity.Question;

public class QuestionDAO {
	protected Connection connection;
	
	public QuestionDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Question createQuestion(ResultSet rs) throws SQLException{
		Question q = new Question();
		q.setId(rs.getInt("id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setChoice1(rs.getString("choice1"));
        q.setChoice2(rs.getString("choice2"));
        q.setChoice3(rs.getString("choice3"));
        q.setChoice4(rs.getString("choice4"));
        q.setCorrectChoice(rs.getInt("correct_choice"));
        q.setExplanation(rs.getString("explanation"));
        q.setImageUrl(rs.getString("image_url"));
        
        return q;
	}
	
	public List<Question> findAll() throws SQLException {
	        List<Question> list = new ArrayList<>();
	        String sql = "SELECT * FROM questions";
	        Statement statement = this.connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while(resultSet.next()) {
	        	Question q = createQuestion(resultSet);
	        	list.add(q);
	        }
	        
	        resultSet.close();
	        statement.close();
	        
	        return list;
	    }
	
	public List<Question> find15Questions() throws SQLException{
		List<Question> list = new ArrayList<>();
		String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 15";
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while(resultSet.next()) {
			Question q = createQuestion(resultSet);
			list.add(q);
		}
		resultSet.close();
		statement.close();
		return list;
	}
	
	public List<Question> find1Question() throws SQLException{
		List<Question> list = new ArrayList<>();
		String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while(resultSet.next()) {
			Question q = createQuestion(resultSet);
			list.add(q);
		}
		resultSet.close();
		statement.close();
		return list;
	}
	 
	 public Question findQuestionById(int id) throws SQLException {
	        String sql = "SELECT * FROM questions WHERE id = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                Question q = new Question();
	                q.setId(rs.getInt("id"));
	                q.setQuestionText(rs.getString("question_text"));
	                q.setChoice1(rs.getString("choice1"));
	                q.setChoice2(rs.getString("choice2"));
	                q.setChoice3(rs.getString("choice3"));
	                q.setChoice4(rs.getString("choice4"));
	                q.setCorrectChoice(rs.getInt("correct_choice"));
	                q.setExplanation(rs.getString("explanation"));
	                q.setImageUrl(rs.getString("image_url"));
	                return q;
	            }
	        }
	        return null;
	    }
	 
	 public int addQuestion(Question q) throws SQLException {
	        String sql = "INSERT INTO questions (question_text, choice1, choice2, choice3, choice4, correct_choice, explanation, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, q.getQuestionText());
	            stmt.setString(2, q.getChoice1());
	            stmt.setString(3, q.getChoice2());
	            stmt.setString(4, q.getChoice3());
	            stmt.setString(5, q.getChoice4());
	            stmt.setInt(6, q.getCorrectChoice());
	            stmt.setString(7, q.getExplanation());
	            stmt.setString(8, q.getImageUrl());
	            return stmt.executeUpdate(); // 成功した行数（1ならOK）
	        }
	    }
	 
	 public int updateQuestion(Question q) throws SQLException {
	        String sql = "UPDATE questions SET question_text=?, choice1=?, choice2=?, choice3=?, choice4=?, correct_choice=?, explanation=?, image_url=? WHERE id=?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, q.getQuestionText());
	            stmt.setString(2, q.getChoice1());
	            stmt.setString(3, q.getChoice2());
	            stmt.setString(4, q.getChoice3());
	            stmt.setString(5, q.getChoice4());
	            stmt.setInt(6, q.getCorrectChoice());
	            stmt.setString(7, q.getExplanation());
	            stmt.setString(8, q.getImageUrl());
	            stmt.setInt(9, q.getId());
	            return stmt.executeUpdate();
	        }
	    }
	 
	 public int deleteQuestion(int id) throws SQLException {
	        String sql = "DELETE FROM questions WHERE id=?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, id);
	            return statement.executeUpdate();
	        }
	    }
	 
	 public int count() throws SQLException{
		 int count = 0;
		 String sql = "SELECT COUNT(*) AS count FROM questions";
		 Statement statement = this.connection.createStatement();
		 ResultSet resultSet = statement.executeQuery(sql);
		 if(resultSet.next()) {
			 count = resultSet.getInt("count");
		 }
		 
		 resultSet.close();
		 statement.close();
		 
		 return count;
	 }
}
