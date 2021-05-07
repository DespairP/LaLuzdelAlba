package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.*;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.BlockLaLuzLeaves;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**BasicTreeFeature是一个抽象继承Feature的类,
 * 该类包含:
 * 指定如何实现该类的Builder,
 * 工具方法,
 * 生成结构的抽象方法.
 * */
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

    /**
     * 具体实现生成逻辑
     * @param reader ServerWorld的抽象world实例
     * @param random 用于计算随机数
     * */
    public abstract boolean doPlace(ISeedReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config);

    /**判定是否有面积生长该植物，请在doPlace()使用该方法*/
    public abstract boolean check(IWorld reader, BlockPos pos,int height,int radius);

    /**树生成时会调用这个逻辑进行生成，此类底层调用BasicTreeFeature#doPlace进行实现*/
    @Override
    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        Set<BlockPos> changedLogs = Sets.newHashSet();
        Set<BlockPos> changedLeaves = Sets.newHashSet();
        MutableBoundingBox mutableboundingbox = MutableBoundingBox.getUnknownBox();
        return doPlace(reader,random,pos,changedLogs,changedLeaves,mutableboundingbox,baseTreeFeatureConfig);
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

        /**设置最小高度*/
        public T setMinHeight(int minHeight){this.minHeight = minHeight; return (T)this;}

        /**设置最大高度*/
        public T setMaxHeight(int maxHeight){this.maxHeight = maxHeight; return (T)this;}

        /**设置原木方块*/
        public T setLogBlock(BlockState logBlock){this.logBlock = logBlock; return (T)this;}

        /**设置树叶方块*/
        public T setLeavesBlock(BlockState leavesBlock){this.leavesBlock = leavesBlock; return (T)this;}

        /**生成BasicTreeFeature*/
        public abstract F build();
    }

    /*---------以下是一些工具方法，部分取自TreeFeature---------*/

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
        return TreeFeature.isAirOrLeaves(reader,pos) || reader.isStateAtPosition(pos,(state)->state.getBlock() instanceof BlockLaLuzLeaves);
    }

    /**该位置上的方块是否能被替换成其他方块*/
    public boolean canReplace(IWorldGenerationBaseReader reader,IWorld world,BlockPos pos){
        return reader.isStateAtPosition(pos, (state)->state.getBlockState().canBeReplacedByLeaves(world ,pos) || state.is(BlockTags.SAPLINGS) || state.getBlock() == Blocks.VINE || state.is(Blocks.AIR) || state.getMaterial().isReplaceable());
    }

    /**直接放置方块*/
    private void setBlockKnownShape(IWorldWriter writer, BlockPos pos, BlockState state) {
        writer.setBlock(pos, state, 19);
    }

    /**
     * 是否能够在这个方块上生成树
     * @param world 世界[Reader模式]
     * @param pos 树生长时，基于的方块
     * */
    public boolean canPlantOn(IWorldReader world, BlockPos pos){
        return world.getBlockState(pos).canSustainPlant(world,pos, Direction.UP,(SaplingBlock)Blocks.OAK_SAPLING);
    }

    /**
     * 生成在半径之内的实心圆
     * @param world 世界
     * @param center 实心圆中心坐标
     * @param blockState 方块的BlockState
     * @param height 生成实心圆的高度(可以通过此生成一个圆柱）
     * @param radius 实心圆半径
     * @param changedSet 之前放置过该方块的所有BlockPos的集合
     * @param boundingBox 树的绑定盒,包含树结构所有放置的方块
     * */
    public void generateCircle(IWorld world,BlockPos center,BlockState blockState,int height,int radius,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        for(int y = 0;y < height;y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x + 0, y + 0, z + 0);
                    if (caculatePointDistance(center, pos) > radius) continue; //如果点到中心的距离大于半径,就不生成该方块
                    setBlock(world, pos, blockState,changedSet,boundingBox);
                }
            }
        }
    }

    /**计算点到中心的距离*/
    private double caculatePointDistance(BlockPos center,BlockPos point){
        int r = Math.abs(point.getX() - center.getX());
        int h = Math.abs(point.getZ() - center.getZ());
        return Math.sqrt(Math.pow(r,2) + Math.pow(h,2));
    }

    /**
     * 生成原木
     * @param world 放置原木的世界World
     * @param pos 放置原木的位置BlockPos
     * @param state 原木方块的BlockState
     * @param changedLogs 放置过原木方块的所有BlockPos的集合
     * @param boundingBox 树的绑定盒,包含树结构所有放置的方块
     * */
    public void setLogBlock(IWorld world,BlockPos pos,BlockState state,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        if(!canReplace(world,world,pos)) return;
        setBlock(world,pos,state,changedLogs,boundingBox);
    }

    /**
     * 生成树叶
     * @param world 放置树叶的世界World
     * @param pos 放置树叶的位置BlockPos
     * @param state 树叶方块的BlockState
     * @param changedLeaves 放置过树叶方块的所有BlockPos的集合
     * @param boundingBox 树的绑定盒,包含树结构所有放置的方块
     * */
    public void setLeavesBlock(IWorld world,BlockPos pos,BlockState state,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox){
        if(!canReplace(world,world,pos)) return;
        setBlock(world,pos,state,changedLeaves,boundingBox);
    }


    /**
     * 放置方块 <br/>
     * 检测set中是否已经包含了该blockPos,如果没有则放置方块,如果有则不放置
     * 放置完成后会加入到树的绑定盒和该方块拥有的Set中
     * */
    private void setBlock(IWorld world,BlockPos pos,BlockState state,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        if(changedSet.contains(pos)) return;
        setBlockKnownShape(world,pos,state);
        changedSet.add(pos.immutable());
        boundingBox.expand(new MutableBoundingBox(pos.immutable(),pos.immutable()));
    }

    /**
     * 通过多个blockPos放置方块
     * @param world 世界
     * @param state 需要放置的方块的BlockState
     * @param changedSet 所有放置过该方块的BlockPos的集合Set
     * @param boundingBox 树的绑定盒,包含树结构所有放置的方块
     * @param blockPos 多个blockPos，在多个blockPos都会放置这个方块
     * */
    public void setBlocks(IWorld world,BlockState state,Set<BlockPos> changedSet,MutableBoundingBox boundingBox,BlockPos ...blockPos){
        if(blockPos == null) return;
        Arrays.stream(blockPos).forEach(pos->{
            setBlock(world,pos,state,changedSet,boundingBox);
        });
    }

    /**
     * 随机L字方向生成方块,返回生成的x,z轴两个方向(north/south、East/West)
     * @param world 世界
     * @param center 生成的中心点
     * @param state 生成方块的BlockState
     * @param height 生成方块高度
     * @param random 随机实例，用于生成随机数
     * @param changedSet 所有摆放过该方块的Set
     * @param boundingBox 树的绑定盒,包含树结构所有放置的方块
     * @return 返回生成的x,z轴两个方向(north/south、East/West)
     * */
    public Direction[] genRandomL(IWorld world,BlockPos center,BlockState state,int height,Random random,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        Direction[] offsets = {random.nextBoolean()?Direction.EAST:Direction.WEST,
                                random.nextBoolean()?Direction.SOUTH:Direction.NORTH};
        genL(world,center,state,height,offsets,changedSet,boundingBox);
        return offsets;
    }



    /**
     * 按L字生成方块
     * */
    public void genL(IWorld world,BlockPos center,BlockState state,int height,Direction[] side,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        if(side == null || side.length != 2) return;
        for(int y = 0; y < height;y++){
            setBlocks(world,state,changedSet,boundingBox,center.offset(0,y,0),
                    center.offset(side[0].getStepX(),y,0),
                    center.offset(0,y,side[1].getStepZ()));
        }
    }

    /**?*/
    protected VoxelShapePart updateLeaves(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(boundingBox.getXSpan(), boundingBox.getYSpan(), boundingBox.getZSpan());
        int i = 6;

        for(int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(BlockPos blockpos : Lists.newArrayList(changedLeaves)) {
            if (boundingBox.isInside(blockpos)) {
                voxelshapepart.setFull(blockpos.getX() - boundingBox.x0, blockpos.getY() - boundingBox.y0, blockpos.getZ() - boundingBox.z0, true, true);
            }
        }

        for(BlockPos blockpos1 : Lists.newArrayList(changedLogs)) {
            if (boundingBox.isInside(blockpos1)) {
                voxelshapepart.setFull(blockpos1.getX() - boundingBox.x0, blockpos1.getY() - boundingBox.y0, blockpos1.getZ() - boundingBox.z0, true, true);
            }

            for(Direction direction : Direction.values()) {
                blockpos$mutable.setWithOffset(blockpos1, direction);
                if (!changedLogs.contains(blockpos$mutable)) {
                    BlockState blockstate = world.getBlockState(blockpos$mutable);
                    if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
                        list.get(0).add(blockpos$mutable.immutable());
                        setBlockKnownShape(world, blockpos$mutable, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
                        if (boundingBox.isInside(blockpos$mutable)) {
                            voxelshapepart.setFull(blockpos$mutable.getX() - boundingBox.x0, blockpos$mutable.getY() - boundingBox.y0, blockpos$mutable.getZ() - boundingBox.z0, true, true);
                        }
                    }
                }
            }
        }

        for(int l = 1; l < 6; ++l) {
            Set<BlockPos> set = list.get(l - 1);
            Set<BlockPos> set1 = list.get(l);

            for(BlockPos blockpos2 : set) {
                if (boundingBox.isInside(blockpos2)) {
                    voxelshapepart.setFull(blockpos2.getX() - boundingBox.x0, blockpos2.getY() - boundingBox.y0, blockpos2.getZ() - boundingBox.z0, true, true);
                }

                for(Direction direction1 : Direction.values()) {
                    blockpos$mutable.setWithOffset(blockpos2, direction1);
                    if (!set.contains(blockpos$mutable) && !set1.contains(blockpos$mutable)) {
                        BlockState blockstate1 = world.getBlockState(blockpos$mutable);
                        if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
                            int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
                            if (k > l + 1) {
                                BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(l + 1));
                                setBlockKnownShape(world, blockpos$mutable, blockstate2);
                                if (boundingBox.isInside(blockpos$mutable)) {
                                    voxelshapepart.setFull(blockpos$mutable.getX() - boundingBox.x0, blockpos$mutable.getY() - boundingBox.y0, blockpos$mutable.getZ() - boundingBox.z0, true, true);
                                }

                                set1.add(blockpos$mutable.immutable());
                            }
                        }
                    }
                }
            }
        }

        return voxelshapepart;
    }
}
