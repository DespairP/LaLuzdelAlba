package teamHTBP.LaLuzdelAlba.WorldGeneration;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;
import teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures.BulbTreeFeature;
import teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures.LowShrubsFeature;
import teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures.MarshSmokyTreeFeature;

public class FeaturesLoader {
    public FeaturesLoader() {
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, LaLuzdelAlba.MODID);

    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> LOW_SHRUBS = FEATURES.register("low_shrubs_tree", ()->new LowShrubsFeature.LowShrubsBuilder(BlocksLoader.SMOKY_LEAVES.get().defaultBlockState(),BlocksLoader.FUNGUS_SMOKY_LOG.get().defaultBlockState(),5).setMinHeight(4).setMaxHeight(6).build());
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> SMOKY_MARSH_TREE = FEATURES.register("marsh_smoky_tree", ()->new MarshSmokyTreeFeature.SmokyMarshTreeFeatureBuilder(BlocksLoader.SMOKY_LEAVES.get().defaultBlockState(),BlocksLoader.MARSH_SMOKY_LOG.get().defaultBlockState(),5,7).setAltLog(BlocksLoader.FUNGUS_SMOKY_LOG.get().defaultBlockState()).build());
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> NORMAL_SMOKY_TREE = FEATURES.register("smoky_normal_tree", ()->new BulbTreeFeature.BulbTreeBuilder(BlocksLoader.SMOKY_LEAVES.get().defaultBlockState(),BlocksLoader.SMOKY_LOG.get().defaultBlockState(),8,12).build());

}
