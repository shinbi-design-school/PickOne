package com.design_shinbi.searchinghome.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.design_shinbi.searchinghome.model.dao.ScoreDAO;
import com.design_shinbi.searchinghome.model.entity.Score;
import com.design_shinbi.searchinghome.util.DBUtil;

class ScoreTest {
    private Connection connection;
    private ScoreDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        connection = DBUtil.connect(true);
        connection.setAutoCommit(false);

        dao = new ScoreDAO(connection);

        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM scores");
        stmt.execute("DELETE FROM users");

        String insertUserSQL = """
            INSERT INTO users (id, name, email, password, admin, created_at, updated_at) VALUES
            (1, 'ユーザー1', 'test1@example.com', 'dummy', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'ユーザー2', 'test2@example.com', 'dummy', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (3, 'ユーザー3', 'test3@example.com', 'dummy', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """;
        stmt.execute(insertUserSQL);
        stmt.close();

        connection.commit();
    }



    @AfterEach
    void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testSaveAndFindByUserId() throws Exception {
        Score score = new Score(1, 85); // userId=1, score=85
        dao.save(score);

        List<Score> results = dao.findByUserId(1);
        assertEquals(1, results.size());

        Score result = results.get(0);
        assertEquals(1, result.getUserId());
        assertEquals(85, result.getScore());
        assertNotNull(result.getPlayedAt());
    }

    @Test
    void testFindTopScore() throws Exception {
        // 複数データを追加
        dao.save(new Score(1, 70));
        dao.save(new Score(2, 90));
        dao.save(new Score(3, 80));

        List<Score> topScores = dao.findTopScore(2);

        assertEquals(2, topScores.size());
        assertEquals(90, topScores.get(0).getScore());
        assertEquals(80, topScores.get(1).getScore());
    }
}
