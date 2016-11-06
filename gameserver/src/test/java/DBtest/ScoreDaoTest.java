package DBtest;

import model.dao.ScoreDao;
import org.junit.Test;
import server.data.Score;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 07.11.2016.
 */
public class ScoreDaoTest {
    private ScoreDao scoreDao = new ScoreDao();
    private Score tempScore = new Score()
            .setUserId(1)
            .setScore(new Long(1000))
            .setId(0);

    @Test
    public void insertTest() throws Exception {
        int before = scoreDao.getAll().size();
        scoreDao.insert(tempScore);
        assertEquals(before + 1, scoreDao.getAll().size());
    }

}
