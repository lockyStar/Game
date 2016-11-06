package server.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Alex on 07.11.2016.
 */

public class Score {
    private static final Logger log = LogManager.getLogger(Authentication.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = false)
    private Long score;

    @Column(nullable = false)
    private int userId;

    /**
     *
     * @param id
     * @return
     */
    public Score setId (int id){
        this.id = id;
        return this;
    }

    /**
     *
     * @param id
     * @return
     */
    public Score setUserId(int id){
        this.userId = userId;
        return this;
    }

    /**
     *
     * @param score
     * @return
     */
    public Score setScore(Long score){
        this.score = score;
        return this;
    }

    public int getUserId(){
        return this.userId;
    }

    public int getId(){
        return this.id;
    }

    public Long getScore(){
        return this.score;
    }
}
