package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alex on 11.10.2016.
 *
 * @author Alexlocky
 *
 */
public class BattleField {
    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);


    public BattleField() {
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Battle Field{" +

                '}';
    }

}
