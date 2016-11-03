package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.util.Random;

import static model.GameConstants.MAX_FOOD_CIRCLE_SIZE;


/**
 * Created by Alex on 09.10.2016.
 *
 * @author Alexlocky
 */
public class FoodCircle {
    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
    @NotNull
    private int points;

    /**
     * Create new FoodCircle
     *
     */
    public FoodCircle() {
        Random randomInt = new Random();
        this.points = randomInt.nextInt(MAX_FOOD_CIRCLE_SIZE) + 1;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "FoodCircle{" +
                "points='" + this.points + '\'' +
                '}';
    }

}
