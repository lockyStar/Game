package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import server.auth.Token;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;

  private int size = 1;

  private int score = 0;

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
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
    return "Player{" +
        "name='" + this.name + '\'' +
        "size='" + this.size + '\'' +
        "score='" + this.score + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object obj){
    if (obj == null) {
      return false;
    }
    if (!Player.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final Player other = (Player) obj;
    log.info (name + " vs " + other.getName() );
    if (!this.name.equals(other.getName())) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode(){
    return name.hashCode();
  }
}
