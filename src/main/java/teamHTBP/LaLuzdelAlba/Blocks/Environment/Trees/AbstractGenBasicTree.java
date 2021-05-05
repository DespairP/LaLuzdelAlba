package teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

/**此类旨在只使用Feature，而不使用ConfigFeature进行树的生成*/
public abstract class AbstractGenBasicTree extends Tree {
    /**不使用ConfigFeature*/
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasFlower) {
        return null;
    }

    /**获取Feature*/
    protected abstract Feature<BaseTreeFeatureConfig> getFeature();

    @Override
    public boolean growTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState blockState, Random random) {
        Feature<BaseTreeFeatureConfig> feature = this.getFeature();
        if(feature == null) return false;
        world.setBlock(pos, Blocks.AIR.defaultBlockState(),4);
        if(feature.place(world,chunkGenerator,random,pos, Features.OAK.config())) return true;
        return false;
    }
}
