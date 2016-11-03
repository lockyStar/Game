package server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 24.10.2016.
 */
public class Token {
    private static final Logger log = LogManager.getLogger(Authentication.class);

    private Long token;
    public Token() {
        token = ThreadLocalRandom.current().nextLong();
    }

    public Token(Long token){
        this.token = token;
    }

    @Override
    public int hashCode (){
        return Long.hashCode(token);
    }

    public Token(String rawToken){
        rawToken = rawToken.substring("Bearer".length()).trim();
        token = Long.parseLong(rawToken);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!Token.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Token other = (Token) obj;
        if (!this.token.equals(other.getToken())) {
            return false;
        }
        return true;
    }

    public Long getToken(){
        return token;
    }

    @Override
    public String toString() {
        return Long.toString(token);
    }

}
