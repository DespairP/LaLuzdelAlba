package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

import java.util.Random;
import java.util.Set;

/**低矮灌木树Feature*/
public class LowShrubsFeature extends BasicTreeFeature{
    /**灌木丛*/
    protected final int foliageNumber;


    public LowShrubsFeature(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock, int minHeight, int maxHeight,int foliageNumber) {
        super(leavesBlock, logBlock, vineBlock, minHeight, maxHeight);
        this.foliageNumber = foliageNumber;
    }

    /***/
    @Override
    public boolean doPlace(IWorldGenerationReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config) {
        return false;
    }

    /**Builder*/
    public static class LowShrubsBuilder extends Builder<LowShrubsBuilder,LowShrubsFeature>{
        /**灌木丛树叶个数*/
        protected int foliageNumber = 0;

        public LowShrubsBuilder(BlockState leavesBlock, BlockState logBlock, int foliageNumber) {
            super(leavesBlock, logBlock);
            this.foliageNumber = foliageNumber;
        }

        public LowShrubsBuilder(BlockState leavesBlock, BlockState logBlock) {
            super(leavesBlock, logBlock);
        }

        public LowShrubsBuilder setFoliageNumber(int foliageNumber){this.foliageNumber = foliageNumber; return this;}

        @Override
        public LowShrubsFeature build() {
            return new LowShrubsFeature(leavesBlock,logBlock,vineBlock,minHeight,maxHeight,foliageNumber);
        }
    }


}
