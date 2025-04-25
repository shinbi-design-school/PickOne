package com.design_shinbi.searchinghome.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.design_shinbi.searchinghome.model.dao.UserDAO;
import com.design_shinbi.searchinghome.model.entity.User;
import com.design_shinbi.searchinghome.util.DBUtil;
import com.design_shinbi.searchinghome.util.PasswordUtil;

public class UserTest {

    private static Connection connection;
    private static UserDAO dao;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        connection = DBUtil.connect(true); // テスト用DBに接続
        dao = new UserDAO(connection);
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddFindAndDeleteUser() throws Exception {
        // 初期件数
        List<User> before = dao.findAll();
        int originalSize = before.size();

        // ユーザー追加
        String name = "テストユーザー";
        String email = "test@example.com";
        String password = "testpass";
        boolean admin = true;

        dao.add(name, email, password, admin);

        // 確認: 件数が1増えていること
        List<User> afterInsert = dao.findAll();
        assertEquals(originalSize + 1, afterInsert.size());

        // 追加されたユーザー取得（最後の要素）
        User added = afterInsert.get(afterInsert.size() - 1);
        assertEquals(name, added.getName());
        assertEquals(email, added.getEmail());
        assertTrue(added.isAdmin());

        //パスワードのハッシュ確認
        String salt = added.getSalt();
        String expectedHash = PasswordUtil.hashPasswordWithSaltPepper(password, salt);  // 修正部分
        assertEquals(expectedHash, added.getPassword(), "ハッシュが一致していること");

        System.out.println("✅ ユーザー追加成功: " + added);

        // 削除
        dao.delete(added.getId());

        // 確認: 件数が元に戻っていること
        List<User> afterDelete = dao.findAll();
        assertEquals(originalSize, afterDelete.size());

        System.out.println("✅ ユーザー削除成功");
    }
}

