package DBtest;

import model.dao.ScoreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import server.auth.Authentication;
import server.data.Score;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 07.11.2016.
 */
public class ScoreDaoTest {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private ScoreDao scoreDao = new ScoreDao();
    private static Score tempScore = new Score(4,2000);

    @Test
    public void insertTest() throws Exception {
        int before = scoreDao.getAll().size();
        log.info("temp score " + tempScore.getUserId()+ " " +tempScore.getScore());
        scoreDao.insert(tempScore);
        assertEquals(before + 1, scoreDao.getAll().size());
    }
    @Test
    public void updateTest() throws Exception{
        Score tempScore2 = new Score(4,3000);
        //List<Score> oldscores = scoreDao.getAllWhere("userid = 4");
        scoreDao.update(tempScore2);
        //List<Score> newscores = scoreDao.getAllWhere("userid = 4");
        //int newscore = newscores.get(0).getScore();
        //assertEquals(newscore,3000);
    }

    @Test
    public void existenceTest() throws Exception{
        scoreDao.checkExistance();
    }
}
