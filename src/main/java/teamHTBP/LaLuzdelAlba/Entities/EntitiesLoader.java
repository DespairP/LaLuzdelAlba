package teamHTBP.LaLuzdelAlba.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static teamHTBP.LaLuzdelAlba.LaLuzdelAlba.MODID;

public class EntitiesLoader {
    public EntitiesLoader(){
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public final static DeferredRegister<EntityType<?>> ENTITIES =DeferredRegister.create(ForgeRegistries.ENTITIES,MODID);

    public final static RegistryObject<EntityType<EntityMushroomWizard>> MUSHROOM_WIZARD = ENTITIES.register("mushroom_wizard", ()->EntityType.Builder.of(EntityMushroomWizard::new, EntityClassification.MISC).sized(1.0f,1.0f).build("mushroom_wizard"));
}
