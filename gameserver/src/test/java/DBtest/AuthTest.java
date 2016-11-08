package DBtest;

import model.dao.TokenDao;
import model.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import server.auth.Authentication;
import server.auth.Token;
import server.auth.User;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 08.11.2016.
 */
public class AuthTest {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static final UserDao userDao = new UserDao();
    private static final TokenDao tokenDao = new TokenDao();


    @Test
    public void registerTest() {
        int before = userDao.getAll().size();

        Authentication auth = new Authentication();
        List<User> tempusers = userDao.getAllWhere("name = 'LOL'");
        if (tempusers.size() == 0) {

            auth.register("LOL", "Pass");
            assertEquals(before + 1, userDao.getAll().size());

        }
        else {
            auth.register("LOL", "Pass");
            assertEquals(before, userDao.getAll().size());
        }

    }

    @Test
    public void loginTest() {
        int before = userDao.getAll().size();

        Authentication auth = new Authentication();
        List<User> tempUsers = userDao.getAllWhere("name = 'LOL'");
        if (tempUsers.size() == 0) {
            before = tokenDao.getAll().size();
            auth.authenticateUser("LOL", "Pass");
            assertEquals(before, tokenDao.getAll().size());

        }
        else {
            auth.authenticateUser("LOL", "Pass");
            List<Token> newToken = tokenDao.getAllWhere("userId = " + tempUsers.get(0).getId());

            assertEquals(newToken.get(0).getUserId(), tempUsers.get(0).getId());
        }

    }


}