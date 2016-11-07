package server.auth;

import model.Player;
import server.data.Score;
import model.dao.UserDao;
import model.dao.TokenDao;
import model.dao.ScoreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alex on 24.10.2016.
 */
public class TokenContainer{
    private static ConcurrentHashMap<User, String> credentials;
    private static ConcurrentHashMap<User, Player> players;
    private static ConcurrentHashMap<User , Token> tokens;
    private static ConcurrentHashMap<Token, User> tokensReversed;

    private static final Logger log = LogManager.getLogger(Authentication.class);

    static {
        credentials = new ConcurrentHashMap<>();
        Player playerAdmin = new Player("admin");
        User admin = new User("admin");
        credentials.put(admin, "admin");
        tokens = new ConcurrentHashMap<>();
        Token token = new  Token(1L);
        tokens.put(admin, token);
        tokensReversed = new ConcurrentHashMap<>();
        tokensReversed.put(token, admin);
        log.info(credentials.get(admin));
        players = new ConcurrentHashMap<>();
        players.put(admin, playerAdmin);
    }

    private static UserDao userDao = new UserDao();
    private static TokenDao tokenDao = new TokenDao();
    private static ScoreDao scoreDao = new ScoreDao();

    public static boolean addUser(User user) {
        /*if (credentials.putIfAbsent(user, password) != null) {
            log.info(user.toString() + "not adding user");
            return true;
        }
        log.info(user.toString() + "adding user");
        players.put(user, new Player(user.getName()));

        return false;
        */
        List<User> oldUsers = userDao.getAllWhere("name = '" + user.getName() + "'");
        log.info(" size of oldusers with this name is {} ", oldUsers.size());
        if (oldUsers.size() == 0) {
            log.info(" size of oldusers with this name {} is null", user.getName());
            userDao.insert(user);
            List<User> users = userDao.getAllWhere("name = '" + user.getName() + "'");
            Score score = new Score(users.get(0).getName(),0);
            scoreDao.insert(score);
            return true;
        }
        return false;
    }

    public static User getUserByString (String nick){
        /*User temp  = new User();
        for (Enumeration<User> e = credentials.keys(); e.hasMoreElements();){
            temp = e.nextElement();
            if (temp.getName().equals(nick)){
                //log.info("Match");
                return temp;
            }
        }
        return null;
        */
        List<User> oldUsers = userDao.getAllWhere("name = '" + nick + "'");
        if (oldUsers.size() == 1){
            return oldUsers.get(0);
        }
        User tempUser = new User(null).setPassword(null);
        return tempUser;
    }


    public static void logCred(){
        for (Enumeration<User> e = credentials.keys(); e.hasMoreElements();){
            log.info("checking " + e.nextElement().getName());
        }
    }

    public static String getUserNameByToken(String rawToken) {
        return tokensReversed.get(new Token(rawToken)).getName();
    }

    public static String getUserNameByToken(Long token) {
        return tokensReversed.get(new Token(token)).getName();
    }

    public static User getUserByToken(String rawToken) {
        return tokensReversed.get(new Token(rawToken));
    }

    public static User getUserByToken(Long token) {
        return tokensReversed.get(new Token(token));
    }


    static Token issueToken(User user) {
        List<Token> oldTokens = tokenDao.getAllWhere("userId = '" + user.getId() + "'");
        if (oldTokens.size() == 1){
            return oldTokens.get(0);
        }
        Token newtoken = new Token().setUserId(user.getId());
        tokenDao.insert(newtoken);
        List<Score> scores = scoreDao.getAllWhere("username = '" + user.getName() + "'");
        Score score = scores.get(0);
        score.setScore(score.getScore() + 2);
        scoreDao.updateScore(score);
        return newtoken;
}


    static boolean authenticate(User user,String nick , String password) throws Exception {
        //List<User> oldUsers = userDao.getAllWhere("name = '" + user + "'");

        log.info("passwords: " + password + " vs " + user.getPassword());
        return (password.equals(user.getPassword())&&(nick.equals(user.getName())));
    }


    static void validateToken(Token token) throws Exception {

        /*if (!tokensReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }*/
        log.info("Entered in validateToken");
        List<Token> tokens = tokenDao.getAllWhere("token = '" + token.getToken() + "'");
        log.info("List of tokens accepted with token " + token.getToken());
        log.info("Size of list " + tokens.size());
        if (tokens.size() == 0) {
            log.info("I throwed exception");
            throw new Exception("Token validation exception");
        }
        log.info("Correct token");
    }

    public static String renameUser(Token token, String name ){
        List<User> tempusers = userDao.getAllWhere("name = '" + name + "'");
        if (tempusers.size() == 0){
            List<Token> oldTokens = tokenDao.getAllWhere("token = '" + token.getToken() + "'");
            Token temp = oldTokens.get(0);
            List<User> oldUsers = userDao.getAllWhere("id = '" + temp.getUserId() + "'");
            User tempuser = oldUsers.get(0);
            String oldname = tempuser.getName();
            tempuser.setName(name);
            userDao.update(tempuser);

            List<Score> scores = scoreDao.getAllWhere("username = '" + oldname + "'");
            log.info("Size of queried scores {}", scores.size());
            Score score = scores.get(0);
            scoreDao.updateName(score,name);
            return oldname;
            }
        else{
            return name;
        }
    }

    public static String changePassword(Token token,String newpassword){
        List<Token> oldTokens = tokenDao.getAllWhere("token = '" + token.getToken() + "'");
        Token temp = oldTokens.get(0);
        List<User> oldUsers = userDao.getAllWhere("id = '" + temp.getUserId() + "'");
        User tempuser = oldUsers.get(0);
        String oldpass = tempuser.getPassword();
        tempuser.setPassword(newpassword);
        userDao.update(tempuser);
        return oldpass;
    }

    public static String changeEmail(Token token, String newemail){
        List<Token> oldTokens = tokenDao.getAllWhere("token = '" + token.getToken() + "'");
        Token temp = oldTokens.get(0);
        List<User> oldUsers = userDao.getAllWhere("id = '" + temp.getUserId() + "'");
        User tempuser = oldUsers.get(0);
        String oldemail = tempuser.getEmail();
        tempuser.setEmail(newemail);
        userDao.update(tempuser);
        return oldemail;
    }


    static String removeToken(Long token){
        List<Token> oldTokens = tokenDao.getAllWhere("token = '" + token + "'");
        if (oldTokens.size() == 1){
            Token newtoken = oldTokens.get(0);
            tokenDao.delete(newtoken);
            return "Token deleted";
        }
        else{
            return "Logout failed";
        }

    }

    public static String writeUsersJson(){
        List<Token> oldTokens = tokenDao.getAll();
        ArrayList<User> loggedUsers = new ArrayList<>();
        for (Token element : oldTokens){
            List<User> oldUsers = userDao.getAllWhere("id = '" + element.getUserId() + "'");
            User temp = oldUsers.get(0);
            loggedUsers.add(temp);
        }
        log.info(loggedUsers.toString());
        return loggedUsers.toString();
    }

    public static ArrayList<Score> getScoreList(int N) {
        log.info("Entered to write top json ");
        List<Score> scores = scoreDao.getN(N);
        log.info("Size of scores list {} ", scores.size());
        return new ArrayList<>(scores);
    }
}

