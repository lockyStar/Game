package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import server.auth.Token;
import server.auth.User;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 06.11.2016.
 */
public class TokenDao implements Dao<Token> {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    @Override
    public List<Token> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Token").list());
    }

    @Override
    public List<Token> getAllWhere(String... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Token where " + totalCondition).list());
    }

    /**
     * @param token
     */
    @Override
    public void insert (Token token) { Database.doTransactional(session -> session.save(token));}

    public void delete (Token token) {
        Database.delete(token);
    }

    public void update(Token token){
        Database.update(token);
    }
}
