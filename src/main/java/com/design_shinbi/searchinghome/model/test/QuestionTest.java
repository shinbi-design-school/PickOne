package com.design_shinbi.searchinghome.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.design_shinbi.searchinghome.model.dao.QuestionDAO;
import com.design_shinbi.searchinghome.model.entity.Question;
import com.design_shinbi.searchinghome.util.DBUtil;

class QuestionTest {private static Connection connection;
private static QuestionDAO dao;

@BeforeAll
public static void setUpBeforeClass() throws Exception {
    connection = DBUtil.connect(true); // テスト用DBに接続
    dao = new QuestionDAO(connection);
}

@AfterAll
public static void tearDownAfterClass() throws Exception {
    if (connection != null && !connection.isClosed()) {
        connection.close();
    }
}

@Test
public void testCountAfterInsertAndDelete() throws SQLException {
    System.out.println("▶ テスト開始");

    int before = dao.count();
    System.out.println("現在の件数: " + before);

    Question q = new Question();
    q.setQuestionText("テスト問題");
    q.setChoice1("選択肢1");
    q.setChoice2("選択肢2");
    q.setChoice3("選択肢3");
    q.setChoice4("選択肢4");
    q.setCorrectChoice(2);
    q.setExplanation("これはテスト用の問題です。");
    q.setImageUrl("test.jpg");

    int result = dao.addQuestion(q);
    assertEquals(1, result, "1件追加されるはず");

    int afterInsert = dao.count();
    System.out.println("追加後の件数: " + afterInsert);
    assertEquals(before + 1, afterInsert, "件数が1増えているはず");

    Question added = dao.findAll().get(dao.findAll().size() - 1);
    int addedId = added.getId();

    int deleteResult = dao.deleteQuestion(addedId);
    assertEquals(1, deleteResult, "1件削除されるはず");

    int afterDelete = dao.count();
    System.out.println("削除後の件数: " + afterDelete);
    assertEquals(before, afterDelete, "件数が元に戻るはず");

    System.out.println("✅ テスト成功");
}

}