package DBtest;

import model.dao.ScoreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import server.auth.Authentication;
import server.data.DataProvider;
import server.data.Score;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static server.auth.TokenContainer.getScoreList;

/**
 * Created by Alex on 08.11.2016.
 */
public class LeaderboardTest {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ScoreDao scoreDao = new ScoreDao();

    @Test
    public void boardTest(){
        ArrayList<Score> ScoreList = getScoreList(2);
        String test = "Top users : " + Score.writeJSON(ScoreList);
        log.info(test);
        test = test.substring("Top users : ".length()).trim();
        log.info(ScoreList);
        assertEquals(Score.parseJSON(test),ScoreList);

    }

}
