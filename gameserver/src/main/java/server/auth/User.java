package server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alex on 24.10.2016.
 */
public class User {
    @NotNull
    private static final Logger log = LogManager.getLogger(User.class);
    @NotNull
    private String name;

    /**
     * Create new User
     *
     * @param name        visible name
     */
    public User(@NotNull String name) {
        this.name = name;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final User other = (User) obj;
        log.info (name + " vs " + other.getName() );
        if (!this.name.equals(other.getName())) {
            return false;
        }
        log.info("Correct");
        return true;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

}
