package DBtest;

import model.dao.ScoreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import server.auth.Authentication;
import server.data.Score;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
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
    public void getAllTest() throws Exception{
        System.out.println(scoreDao.getAll());
    }


    @Test
    public void getNTest() throws Exception{
        int N = 2;
        log.info(scoreDao.getN(N));
        assertEquals(N, scoreDao.getN(N).size());
    }

    @Test
    public void writeJSONTest() throws Exception{
        ArrayList<Score> toJSON = new ArrayList<>();
        toJSON.add(tempScore);
        toJSON.add(new Score().setUsername("lolo").setScore(14));
        toJSON.add(new Score().setUsername("arata").setScore(11));
        assertArrayEquals(toJSON.toArray() ,Score.parseJSON(Score.writeJSON(toJSON)).toArray());
    }

    @Test
    public void existenceTest() throws Exception{
        scoreDao.checkExistance();
    }
}
