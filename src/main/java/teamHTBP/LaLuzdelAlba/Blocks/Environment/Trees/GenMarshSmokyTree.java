package teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import teamHTBP.LaLuzdelAlba.WorldGeneration.FeaturesLoader;

public class GenMarshSmokyTree extends AbstractGenBasicTree{
    @Override
    protected Feature<BaseTreeFeatureConfig> getFeature() {
        return FeaturesLoader.SMOKY_MARSH_TREE.get();
    }
}
