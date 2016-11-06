package server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by Alex on 24.10.2016.
 */
@Entity
@Table(name = "users")
public class User {
    @NotNull
    private static final Logger log = LogManager.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Date date;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private int tokenId;

    /**
     * Create new User
     *
     * @param name        visible name
     */
    public User(@NotNull String name) {
        this.name = name;
        this.date = new Date();
       // if (log.isInfoEnabled()) {
       //     log.info(toString() + " created");
        //}
    }

    public void setName(String newName){
        name = newName;
    }

    public User setPassword(@NotNull String newPassword) { this.password = newPassword; return this; }

    public User setEmail(String newEmail) { this.email = newEmail; return this; }

    public User setTokenId (int newTokenId) { this.tokenId = newTokenId; return this; }

    public int getId () { return this.id; }

    public String getName(){
        return name;
    }

    public Date getDate() { return this.date; }

    public String getPassword() { return this.password; }

    public String getEmail() { return this.email; }

    public int getTokenId() { return this.tokenId; }

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
        //log.info (name + " vs " + other.getName() );
        if (!this.name.equals(other.getName())) {
            return false;
        }
        //log.info("Correct");
        return true;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    public User(){}
}
