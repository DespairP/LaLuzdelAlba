package teamHTBP.LaLuzdelAlba.Utils;

import net.minecraft.util.Direction;

import java.util.Random;

public class RandomUtils {

    /**
     * 来自BOP，min~max之间生成一个整型随机数
     * @return 如果min<max返回0,否则返回一个介于minValue和maxValue的随机数
     * */
    public static int nextIntBetween(Random random,int minValue,int maxValue){
        if(minValue >= maxValue) return 0;
        return minValue + random.nextInt(1 + maxValue - minValue);
    }

    public static Direction nextDirection(Random random,Direction direction_1,Direction direction_2){
       return random.nextBoolean()?direction_1:direction_2;
    }

}
