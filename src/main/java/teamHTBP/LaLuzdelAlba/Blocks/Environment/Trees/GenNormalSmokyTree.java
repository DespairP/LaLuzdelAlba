package teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import teamHTBP.LaLuzdelAlba.WorldGeneration.FeaturesLoader;

public class GenNormalSmokyTree extends AbstractGenBasicTree{
    @Override
    protected Feature<BaseTreeFeatureConfig> getFeature() {
        return FeaturesLoader.NORMAL_SMOKY_TREE.get();
    }
}
