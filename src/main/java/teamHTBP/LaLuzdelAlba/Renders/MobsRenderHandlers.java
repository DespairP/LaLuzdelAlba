package teamHTBP.LaLuzdelAlba.Renders;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamHTBP.LaLuzdelAlba.Entities.EntitiesLoader;
import teamHTBP.LaLuzdelAlba.Renders.MobRenders.RenderMushroomWizard;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class MobsRenderHandlers {

    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(EntitiesLoader.MUSHROOM_WIZARD.get(), RenderMushroomWizard::new);
    }
}
