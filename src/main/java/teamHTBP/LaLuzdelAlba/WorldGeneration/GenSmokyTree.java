package teamHTBP.LaLuzdelAlba.WorldGeneration;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class GenSmokyTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasFlower) {
        return null;
    }

    @Override
    public boolean growTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState blockState, Random random) {
        ConfiguredFeature<BaseTreeFeatureConfig, ?> configConfiguredFeature = this.getConfiguredFeature(random,false);
        if(configConfiguredFeature == null) return false;
        world.setBlock(pos, Blocks.AIR.defaultBlockState(),4);

        return true;
    }

    public void growThunk(){

    }

    public void growLeaves(){

    }
}
