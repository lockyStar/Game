package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.util.Random;

import static model.GameConstants.MAX_TRAP_SIZE;
import static model.GameConstants.MIN_TRAP_SIZE;

/**
 * Created by Alex on 09.10.2016.
 *
 * @author Alexlocky
 *
 */
public class Trap {
    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
    @NotNull
    private int size;

    /**
     * Create new Trap
     *
     */
    public Trap() {
        Random randomInt = new Random();
        this.size = randomInt.nextInt(MAX_TRAP_SIZE) + MIN_TRAP_SIZE;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Trap{" +
                "size='" + this.size + '\'' +
                '}';
    }

}
