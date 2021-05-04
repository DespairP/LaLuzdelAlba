package teamHTBP.LaLuzdelAlba.Renders;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BlockRenderHandlers {
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlocksLoader.SMOKY_LEAVES.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlocksLoader.SPIRAL_LEAVES.get(), RenderType.translucent());
    }

}
