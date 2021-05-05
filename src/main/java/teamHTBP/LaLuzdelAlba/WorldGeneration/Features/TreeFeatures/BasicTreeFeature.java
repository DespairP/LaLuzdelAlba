package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;

import java.util.Random;
import java.util.Set;

public abstract class BasicTreeFeature extends Feature<BaseTreeFeatureConfig> {
    /**Feature中会涉及的树叶方块*/
    protected final BlockState leavesBlock;
    /**Feature中会涉及的原木方块*/
    protected final BlockState logBlock;
    /**Feature中会涉及的藤蔓方块*/
    protected final BlockState vineBlock;

    /**Feature生成树的最低高度*/
    protected int minHeight = 0;
    /**Feature生成树的最高高度*/
    protected int maxHeight = 0;


    /**使用basicTreeFeature作为树的Feature
     * @deprecated  不使用任何方块，会生成一个空气树
     * */
    @Deprecated
    protected BasicTreeFeature() {
        super(BaseTreeFeatureConfig.CODEC.stable());
        this.leavesBlock = Blocks.AIR.defaultBlockState();
        this.logBlock = Blocks.AIR.defaultBlockState();
        this.vineBlock = Blocks.AIR.defaultBlockState();
    }

    protected BasicTreeFeature(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock, int minHeight, int maxHeight) {
        super(BaseTreeFeatureConfig.CODEC.stable());
        this.leavesBlock = leavesBlock;
        this.logBlock = logBlock;
        this.vineBlock = vineBlock;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    /**BasicTreeFeature的Builder*/
    public abstract static class Builder<T extends Builder,F extends BasicTreeFeature>{
        /**Feature中会涉及的树叶方块*/
        protected BlockState leavesBlock;
        /**Feature中会涉及的原木方块*/
        protected BlockState logBlock;
        /**Feature中会涉及的藤蔓方块*/
        protected BlockState vineBlock;

        /**Feature生成树的最低高度*/
        protected int minHeight = 0;
        /**Feature生成树的最高高度*/
        protected int maxHeight = 0;

        /**一个基于自定义树叶、自定义原木构建一个BasicTreeFeature的builder*/
        public Builder(BlockState leavesBlock, BlockState logBlock) {
            this.leavesBlock = leavesBlock;
            this.logBlock = logBlock;
        }

        /**
         * 默认无参Builder,默认会会使用原版的原木和树叶，
         * 可以使用setLeaves和setLogs进行覆盖
         * */
        public Builder() {
            this.leavesBlock = Blocks.OAK_LEAVES.defaultBlockState();
            this.logBlock = Blocks.OAK_LOG.defaultBlockState();
        }

        public Builder(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock) {
            this.leavesBlock = leavesBlock;
            this.logBlock = logBlock;
            this.vineBlock = vineBlock;
        }

        public T setMinHeight(int minHeight){this.minHeight = minHeight; return (T)this;}

        public T setMaxHeight(int maxHeight){this.maxHeight = maxHeight; return (T)this;}

        public T setLogBlock(BlockState logBlock){this.logBlock = logBlock; return (T)this;}

        public T setLeavesBlock(BlockState leavesBlock){this.leavesBlock = leavesBlock; return (T)this;}

        /**生成BasicTreeFeature*/
        public abstract F build();
    }

    /**树生成时会调用这个逻辑进行生成，此类底层调用BasicTreeFeature#doPlace进行实现*/
    @Override
    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        Set<BlockPos> changedLogs = Sets.newHashSet();
        Set<BlockPos> changedLeaves = Sets.newHashSet();
        MutableBoundingBox mutableboundingbox = MutableBoundingBox.getUnknownBox();
        return doPlace(reader,random,pos,changedLogs,changedLeaves,mutableboundingbox,baseTreeFeatureConfig);
    }

    /**具体实现生成逻辑*/
    public abstract boolean doPlace(IWorldGenerationReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config);

    /*--以下是一些工具方法，取自TreeFeature---*/
    /**该pos是否空闲
     * @return pos是否空闲
     * */
    public boolean isFree(IWorldGenerationBaseReader reader, BlockPos pos){
        return TreeFeature.isFree(reader,pos);
    }

    /**该pos是树叶或者空气
     * @return pos有树叶或者空气方块
     * */
    public boolean isAirOrLeaves(IWorldGenerationBaseReader reader, BlockPos pos) {
        return TreeFeature.isAirOrLeaves(reader,pos);
    }






}
