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

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private int id;
    //@Column(unique = false)
    private int score;
    //@Column(nullable = false)
    private int userId;
    public Score (int userid,int score){
        this.userId = userid;
        this.score = score;
    }
    public Score setUserId(int id){
        this.userId = id;
        return this;
    }

    public Score setScore(int score){
        this.score = score;
        return this;
    }

    public int getUserId(){
        return this.userId;
    }

    //public int getId(){
    //    return this.id;
    //}

    public int getScore(){
        return this.score;
    }
}
