package teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import teamHTBP.LaLuzdelAlba.WorldGeneration.FeaturesLoader;

import javax.annotation.Nullable;
import java.util.Random;

public class GenSmokyTree extends AbstractGenBasicTree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasFlower) {
        return null;
    }

    @Override
    protected Feature<BaseTreeFeatureConfig> getFeature() {
        return FeaturesLoader.LOW_SHRUBS.get();
    }


}
