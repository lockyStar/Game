package server.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;

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
    private String userName;

    public Score (String username, int score){
        this.userName = username;
        this.score = score;
    }

    public Score(){
        this.score = 0;
    }

    public Score setUsername(String username){
        this.userName = username;
        return this;
    }

    public Score setScore(int score){
        this.score = score;

        return this;
    }

    public String getUsername(){
        return this.userName;
    }

    //public int getId(){
    //    return this.id;
    //}

    public int getScore(){
        return this.score;
    }

    @Override
    public String toString() {
        return this.score + " " + this.userName;
    }
}
