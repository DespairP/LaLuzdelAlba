package teamHTBP.LaLuzdelAlba.Renders.ColorMap;

import net.minecraft.world.GrassColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokyGrassColors{
    private static int[] pixels = new int[65536];

    public static void init(int[] p_77479_0_) {
        pixels = p_77479_0_;
    }

    public static int get(double temp, double rainFall) {
        //System.out.println("laluzdelalba:" + temp + " " +rainFall);
        rainFall = rainFall * temp;
        int i = (int)((1.0D - temp) * 255.0D);
        int j = (int)((1.0D - rainFall) * 255.0D);
        int k = j << 8 | i;
        return k > pixels.length ? -65281 : pixels[k];
    }

}
