package DBtest;

import model.dao.TokenDao;
import model.dao.UserDao;
import org.junit.Test;
import server.auth.Token;
import server.auth.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 06.11.2016.
 */
public class DaoTest {
    private UserDao userDao = new UserDao();

    private User lolita;

    {
        lolita = new User("lolita")
                    .setPassword("temp");
        lolitaToken = new Token().setUserId(1);
    }

    private TokenDao tokenDao = new TokenDao();

    private Token lolitaToken;


    @Test
    public void getAllUsersTest() throws Exception {
        System.out.println(userDao.getAll());
    }

    @Test
    public void insertUserTest() throws Exception {
        int before = userDao.getAll().size();
        userDao.insert(lolita);
        assertEquals(before + 1, userDao.getAll().size());
    }

    @Test
    public void getAllToensTest() throws Exception {
        System.out.println(tokenDao.getAll());
    }

    @Test
    public void insertTokensTest() throws Exception {
        int before = tokenDao.getAll().size();
        tokenDao.insert(lolitaToken);
        assertEquals(before + 1, tokenDao.getAll().size());
    }


}