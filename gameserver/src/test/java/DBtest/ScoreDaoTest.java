package DBtest;

import model.dao.ScoreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import server.auth.Authentication;
import server.data.Score;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 07.11.2016.
 */
public class ScoreDaoTest {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private ScoreDao scoreDao = new ScoreDao();
    private static Score tempScore = new Score("Kappa",2000);

    @Test
    public void insertTest() throws Exception {
        int before = scoreDao.getAll().size();
        log.info("temp score " + tempScore.getUsername()+ " " +tempScore.getScore());
        scoreDao.insert(tempScore);
        assertEquals(before + 1, scoreDao.getAll().size());
    }

    @Test
    public void existenceTest() throws Exception{
        scoreDao.checkExistance();
    }
}
