package DBtest;

import model.dao.UserDao;
import org.junit.Test;
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

    }

    @Test
    public void getAllTest() throws Exception {
        System.out.println(userDao.getAll());
    }

    @Test
    public void insertTest() throws Exception {
        int before = userDao.getAll().size();
        userDao.insert(lolita);
        assertEquals(before + 1, userDao.getAll().size());
    }



}