package teamHTBP.LaLuzdelAlba.WorldGeneration;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;
import teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures.LowShrubsFeature;

public class FeaturesLoader {
    public FeaturesLoader() {
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, LaLuzdelAlba.MODID);

    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> LOW_SHRUBS = FEATURES.register("low_shrubs_tree", ()->new LowShrubsFeature.LowShrubsBuilder(BlocksLoader.SMOKY_LEAVES.get().defaultBlockState(),BlocksLoader.FUNGUS_SMOKY_LOG.get().defaultBlockState(),5).setMinHeight(4).setMaxHeight(6).build());

}
