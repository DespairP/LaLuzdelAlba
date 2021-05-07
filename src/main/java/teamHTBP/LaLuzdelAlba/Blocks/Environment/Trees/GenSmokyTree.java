package teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;
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
