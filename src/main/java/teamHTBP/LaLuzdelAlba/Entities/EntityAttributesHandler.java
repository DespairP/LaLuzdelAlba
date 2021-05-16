package teamHTBP.LaLuzdelAlba.Entities;


import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

@Mod.EventBusSubscriber(modid = LaLuzdelAlba.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributesHandler {

    @SubscribeEvent
    public static void setup(final EntityAttributeCreationEvent event) {
        event.put(EntitiesLoader.MUSHROOM_WIZARD.get(),EntityMushroomWizard.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE,0)
                .add(Attributes.MAX_HEALTH,8)
                .add(Attributes.MOVEMENT_SPEED,1).build());
    }
}
