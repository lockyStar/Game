package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.data.Score;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 07.11.2016.
 */
public class ScoreDao implements Dao<Score>{
    private static final Logger log = LogManager.getLogger(ScoreDao.class);

    private static final String SELECT_ALL_SCORES =
            "SELECT * FROM scores";

    private static final String SELECT_ALL_SCORES_WHERE =
            "SELECT * FROM scores where ";

    private static final String INSERT_SCORE_TEMPLATE =
            "INSERT INTO scores (score, userId) VALUES ( %d, %d);";

    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_SCORES);
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        return scores;
    }


    @Override
    public List<Score> getAllWhere(String ... conditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(conditions));
        List<Score> scores = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_SCORES_WHERE + totalCondition);
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        return scores;
    }

    @Override
    public void insert(Score score) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate(String.format(INSERT_SCORE_TEMPLATE, score.getScore(), score.getUserId()));
        } catch (SQLException e) {
            log.error("Failed to add like {}", score, e);
        }
    }

    private static Score mapToScore(ResultSet rs) throws SQLException {
        return new Score(rs.getInt("userId"),rs.getInt("score"));
                //.setUserId(rs.getInt("userId"))
                //.setScore(rs.getLong("score"));
    }
}