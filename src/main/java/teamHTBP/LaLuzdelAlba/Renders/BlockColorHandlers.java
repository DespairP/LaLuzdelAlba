package teamHTBP.LaLuzdelAlba.Renders;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.color.ColorCache;
import net.minecraft.client.resources.ColorMapLoader;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;
import teamHTBP.LaLuzdelAlba.Renders.ColorMap.SmokyGrassColors;

import java.io.IOException;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BlockColorHandlers {
    public static final ColorResolver SMOKY_GRASS_RESOVLER = (biome,temp,rainFall)->SmokyGrassColors.get(biome.getBaseTemperature(),biome.getDownfall());
    private static final ResourceLocation LOCATION = new ResourceLocation(LaLuzdelAlba.MODID,"textures/colormap/smoky_grass.png");
    public static final ColorCache colorCache = new ColorCache();

    @SubscribeEvent
    public static void initColor(FMLClientSetupEvent event)  {
        try {
            SmokyGrassColors.init(ColorMapLoader.getPixels(event.getMinecraftSupplier().get().getResourceManager(), LOCATION));
            System.out.println("init color: xxxxxxxxxxx");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void addBlockColor(ColorHandlerEvent.Block event){
        event.getBlockColors().register(
                (BlockState blockState, IBlockDisplayReader reader, BlockPos pos,int var_4)->{
                    ClientWorld world = Minecraft.getInstance().level;
                    return world != null && pos != null && reader != null?colorCache.getColor(pos,()->world.calculateBlockTint(pos,SMOKY_GRASS_RESOVLER)):SmokyGrassColors.get(0.5,1.0);
                }, BlocksLoader.SMOKY_GRASS.get()
        );
    }
}
