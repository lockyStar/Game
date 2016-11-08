package DBtest;

import model.dao.TokenDao;
import model.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import server.auth.Authentication;
import server.auth.Token;
import server.auth.User;
import server.data.Score;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static server.auth.TokenContainer.getUserByString;

/**
 * Created by Alex on 06.11.2016.
 */
public class DaoTest {
    private UserDao userDao = new UserDao();
    private static final Logger log = LogManager.getLogger(Authentication.class);

    private User lolita;

    {
        lolita = new User("lolittta")
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
    public void writeJSONTest() throws Exception{
        ArrayList<User> toJSON = new ArrayList<>();
        toJSON.add(lolita);
        toJSON.add(new User("lolo").setPassword("124135").setEmail("sdgsdgs@ga.com"));
        toJSON.add(new User("lasasdasolo").setPassword("124asdads135").setEmail("sdgsdgasds@gadsa.com"));
        log.info(User.writeJSON(toJSON));
        assertArrayEquals(toJSON.toArray() ,User.parseJSON(User.writeJSON(toJSON)).toArray());
    }



    @Test
    public void deleteUserTest() throws Exception {
        int before = userDao.getAll().size();
        User lolita2 = getUserByString(lolita.getName());
        userDao.delete(lolita2);
        assertEquals(before - 1, userDao.getAll().size());
    }

    @Test
    public void updateUserTest() throws Exception {
        User first = userDao.getAll().get(0);
        first.setName("lololo");
        userDao.update(first);
        assertEquals(userDao.getAll().get(0).getName(), first.getName());
    }


    @Test
    public void getAllTokensTest() throws Exception {
        System.out.println(tokenDao.getAll());
    }

    @Test
    public void insertTokensTest() throws Exception {
        int before = tokenDao.getAll().size();
        tokenDao.insert(lolitaToken);
        assertEquals(before + 1, tokenDao.getAll().size());
    }


}