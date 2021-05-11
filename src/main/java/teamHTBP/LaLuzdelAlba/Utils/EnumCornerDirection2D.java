package teamHTBP.LaLuzdelAlba.Utils;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Arrays;
import java.util.Random;

import static net.minecraft.util.Direction.*;

public enum EnumCornerDirection2D {
    NORTH_EAST(EAST,NORTH,"north_east",new Vector3i(1,0,-1)),
    SOUTH_EAST(EAST,SOUTH,"south_east",new Vector3i(1,0,1)),
    SOUTH_WEST(WEST,SOUTH,"south_west",new Vector3i(-1,0,1)),
    NORTH_WEST(WEST,NORTH,"north_west",new Vector3i(-1,0,-1));

    private final Direction xDirection;
    private final Direction zDirection;
    private final String name;
    private final Vector3i vec;
    EnumCornerDirection2D(Direction xDirection, Direction zDirection, String name, Vector3i vector) {
        this.xDirection = xDirection;
        this.zDirection = zDirection;
        this.name = name;
        this.vec = vector;
    }

    public int getStepX() {
        return this.vec.getX();
    }

    public int getStepZ() {
        return this.vec.getZ();
    }

    public static EnumCornerDirection2D getRandom(){ return values()[new Random().nextInt(3)]; }

    public EnumCornerDirection2D getClockWise(){
        switch (this){
            case NORTH_EAST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_WEST;
            case NORTH_WEST:
                return NORTH_EAST;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of" + this);
        }
    }

    public EnumCornerDirection2D getCounterClockWise(){
        switch (this){
            case NORTH_EAST:
                return NORTH_WEST;
            case NORTH_WEST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return NORTH_EAST;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public EnumCornerDirection2D getFlip(){
        switch (this){
            case NORTH_EAST:
                return SOUTH_WEST;
            case NORTH_WEST:
                return SOUTH_EAST;
            case SOUTH_WEST:
                return NORTH_EAST;
            case SOUTH_EAST:
                return NORTH_WEST;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }


    public EnumCornerDirection2D getMirror(Direction.Axis axis){
        if(axis == Axis.Z)
            return getZMirror();
        return getXMirror();
    }

    private EnumCornerDirection2D getXMirror(){
        switch (this){
            case NORTH_EAST:
                return SOUTH_EAST;
            case NORTH_WEST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_WEST;
            case SOUTH_EAST:
                return NORTH_EAST;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    private EnumCornerDirection2D getZMirror(){
        switch (this){
            case NORTH_EAST:
                return NORTH_WEST;
            case NORTH_WEST:
                return NORTH_EAST;
            case SOUTH_WEST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return SOUTH_WEST;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public String getName() {
        return name;
    }

    private static final EnumCornerDirection2D[] VALUES = values();


    public static EnumCornerDirection2D fromDirections(Direction directionX,Direction directionZ){
        if(directionX.getAxis() != Axis.X){
           Direction temp = directionX;
           directionX = directionZ;
           directionZ = temp;
        }
        final Direction finalDirectionX = directionX;
        final Direction finalDirectionZ = directionZ;
        return Arrays.stream(VALUES).filter(d2d->d2d.xDirection == finalDirectionX && d2d.zDirection == finalDirectionZ).findFirst().orElseThrow(()-> new IllegalArgumentException("Duplicate keys"));
    }

    public Direction getxDirection() {
        return xDirection;
    }

    public Direction getzDirection() {
        return zDirection;
    }
}
