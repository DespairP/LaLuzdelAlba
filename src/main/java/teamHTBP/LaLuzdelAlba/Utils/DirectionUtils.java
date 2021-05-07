package teamHTBP.LaLuzdelAlba.Utils;

import net.minecraft.util.Direction;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.util.Direction.*;


public class DirectionUtils {
    /**翻转Directions数组中的所有Direction*/
    public static Direction[] flipDirections(Direction[] directions){
        Direction[] flipDirections = new Direction[2];
        AtomicInteger index = new AtomicInteger(0);
        Arrays.stream(directions).forEach((value)->{flipDirections[index.getAndIncrement()] = flip(value) ;});
        return flipDirections;
    }


    public static Direction flip(Direction direction) {
        switch(direction) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + direction);
        }
    }
}
