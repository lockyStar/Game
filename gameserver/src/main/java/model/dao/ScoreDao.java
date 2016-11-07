package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.data.Score;

import java.sql.*;
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

    private static final String SELECT_N_SCORES =
            "SELECT TOP %d * FROM scores";

    private static final String ORDER_BY_DESC =
            " ORDER BY score DESC";

    private static final String SELECT_ALL_SCORES_WHERE =
            "SELECT * FROM scores where ";

    private static final String SELECT_N_SCORES_WHERE =
            "SELECT TOP %d * FROM scores where ";

    private static final String INSERT_SCORE_TEMPLATE =
            "INSERT INTO scores (score, userName) VALUES ( %d, '%s');";

    private static final String CREATE_SCORE =
            "CREATE TABLE IF NOT EXISTS scores\n" +
                    "  (\n" +
                    "      id       SERIAL PRIMARY KEY NOT NULL,\n" +
                    "      score    INTEGER            NOT NULL,\n" +
                    "      userName VARCHAR            NOT NULL\n" +
                    "  );\n";

    private static final String UPDATE_SCORE_TEMPLATE =
            "UPDATE scores SET score=%d WHERE userName='%s';";

    private static final String UPDATE_NAME_TEMPLATE =
            "UPDATE scores SET userName='%s' WHERE userName='%s';";


    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_SCORES + ORDER_BY_DESC);
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        } catch (Exception e){
            checkExistance();
            return Collections.emptyList();
        }
        return scores;
    }


    public List<Score> getN(int N) {
        List<Score> scores = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_N_SCORES + ORDER_BY_DESC,N));
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAllN.", e);
            return Collections.emptyList();
        } catch (Exception e){
            checkExistance();
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
            ResultSet rs = stm.executeQuery(SELECT_ALL_SCORES_WHERE + totalCondition + ORDER_BY_DESC);
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        } catch (Exception e){

            checkExistance();
            return Collections.emptyList();
        }

        return scores;
    }

    public List<Score> getNWhere(int N, String ... conditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(conditions));
        List<Score> scores = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_ALL_SCORES_WHERE + totalCondition + ORDER_BY_DESC,N));
            while (rs.next()) {
                scores.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        } catch (Exception e){

            checkExistance();
            return Collections.emptyList();
        }

        return scores;
    }


    @Override
    public void insert(Score score) {
        checkExistance();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            String Temp = String.format(INSERT_SCORE_TEMPLATE, score.getScore(), score.getUsername());
            stm.executeUpdate(String.format(INSERT_SCORE_TEMPLATE, score.getScore(), score.getUsername()));
        } catch (SQLException e) {
            log.error("Failed to add score {}", score, e);
        }

    }

    public void updateScore(Score score) {

        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate(String.format(UPDATE_SCORE_TEMPLATE, score.getScore(), score.getUsername()));
        } catch (SQLException e) {
            log.error("Failed to add score {}", score, e);
            checkExistance();
            this.insert(score);
        } catch (Exception e){
            checkExistance();
            this.insert(score);
        }

    }

    public void updateName(Score score, String newName) {

        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate(String.format(UPDATE_NAME_TEMPLATE, newName, score.getUsername()));
        } catch (SQLException e) {
            log.error("Failed to add score {}", score, e);
            checkExistance();
            this.insert(score);
        } catch (Exception e){
            checkExistance();
            this.insert(score);
        }

    }

    /**
     * Creates table, if not exists
     */
    public void checkExistance(){
        try (Connection con = DbConnector.getConnection();
            Statement stm = con.createStatement()) {
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "scores", null);
            String query = new String(CREATE_SCORE);
            if (!tables.next()) {
                stm.executeUpdate(query);
            }
            log.info("Table is created");
        } catch (Exception e){
            log.error("Failed to create db", e);
        }


    }

    private static Score mapToScore(ResultSet rs) throws SQLException {
        return new Score()
                .setUsername(rs.getString("userName"))
                .setScore(rs.getInt("score"));
    }
}