package server.auth;

import model.Player;
import model.dao.UserDao;
import model.dao.TokenDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
        /*Token token = tokens.get(user);
        log.info("issue token for " + user.getName());
        if (token != null) {
            return token;
        }

        do {
            token = new Token();
        } while (tokens.containsKey(token));

        tokens.put(user, token);
        tokensReversed.put(token, user);
        return token;
        */
        List<Token> oldTokens = tokenDao.getAllWhere("userId = '" + user.getId() + "'");
        if (oldTokens.size() == 1){
            return oldTokens.get(0);
        }
        Token newtoken = new Token().setUserId(user.getId());
        tokenDao.insert(newtoken);
        return newtoken;
}


    static boolean authenticate(User user,String nick , String password) throws Exception {
        //List<User> oldUsers = userDao.getAllWhere("name = '" + user + "'");

        log.info("passwords: " + password + " vs " + user.getPassword());
        return (password.equals(user.getPassword())&&(nick.equals(user.getName())));
    }


    static void validateToken(Token token) throws Exception {

        if (!tokensReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokensReversed.get(token).getName());
    }

    public static String renameUser(Token token, String name ){
        if (getUserByString(name) == null) {
            User user = tokensReversed.remove(token);
            tokens.remove(user);
            Player player = players.remove(user);
            String pass = credentials.remove(user);
            String previousName = user.getName();
            player.setName(name);
            user.setName(name);
            players.put(user, player);
            credentials.put(user, pass);
            tokensReversed.put(token, user);
            tokens.put(user, token);
            //players.put(name, players.remove(previousName));
            log.info("Changing '{}' name to '{}' was successful", previousName, tokensReversed.get(token));
            log.info("Chg: " + credentials.get(user));
            log.info("Chg: " + players.get(user));
            log.info("Chg: " + tokens.get(user));
            log.info("Chg: " + tokensReversed.get(tokens.get(user)));

            return previousName;
        }
        else {
            return name;
        }
    }

    static String removeToken(Token token){
        User user = tokensReversed.remove(token);
        if (token.equals(tokens.remove(user))) {
            return user.getName();
        }
        return ("Bad try");
    }

    public static String writeUsersJson(){
        String result = "";
        ArrayList<User> list = new ArrayList<>();
        for (Enumeration<User> e =tokens.keys(); e.hasMoreElements();){
             list.add(e.nextElement());
        }
        log.info(list.toString());
        return list.toString();
    }

}

