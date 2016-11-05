package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import server.auth.Token;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 06.11.2016.
 */
public class TokenDao implements Dao<Token> {
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


}
