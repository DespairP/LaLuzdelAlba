package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.BlockLaLuzLeaves;
import teamHTBP.LaLuzdelAlba.Utils.EnumCornerDirection2D;

import javax.annotation.Nonnull;
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
    protected BlockState vineBlock;

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

    /**树生成时会调用这个逻辑进行生成，此类底层调用BasicTreeFeature#doPlace进行实现*/
    @Override
    public boolean place(@Nonnull ISeedReader reader,@Nonnull ChunkGenerator chunkGenerator,@Nonnull Random random,@Nonnull BlockPos pos,@Nonnull BaseTreeFeatureConfig baseTreeFeatureConfig) {
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

        /**
         * 默认无参Builder,默认会会使用原版的原木和树叶，
         * 可以使用setLeaves和setLogs进行覆盖
         * */
        public Builder() {
            this.leavesBlock = Blocks.OAK_LEAVES.defaultBlockState();
            this.logBlock = Blocks.OAK_LOG.defaultBlockState();
            this.vineBlock = Blocks.AIR.defaultBlockState();
        }

        /**一个基于自定义树叶、自定义原木构建一个BasicTreeFeature的builder*/
        public Builder(BlockState leavesBlock, BlockState logBlock) {
            this.leavesBlock = leavesBlock;
            this.logBlock = logBlock;
        }

        /**一个基于自定义树叶、自定义原木、自定义藤蔓构建一个BasicTreeFeature的builder*/
        public Builder(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock) {
            this.leavesBlock = leavesBlock;
            this.logBlock = logBlock;
            this.vineBlock = vineBlock;
        }

        /**一个基于自定义树叶、自定义原木、自定义最大/最小高度构建一个BasicTreeFeature的builder*/
        public Builder(BlockState leavesBlock, BlockState logBlock,int minHeight,int maxHeight) {
            this.leavesBlock = leavesBlock;
            this.logBlock = logBlock;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
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

    /**该pos是草方块或者是泥土方块*/
    public boolean isGrassBlockorDirt(IWorldGenerationBaseReader reader, BlockPos pos){
        return reader.isStateAtPosition(pos,(state)->{return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT);});
    }

    /**该位置上的方块是否能被替换成其他方块*/
    public boolean canReplace(IWorldGenerationBaseReader reader,IWorld world,BlockPos pos){
        return reader.isStateAtPosition(pos, (state)->state.getBlockState().canBeReplacedByLeaves(world ,pos) || state.is(BlockTags.SAPLINGS) || state.is(Blocks.VINE) || state.is(Blocks.AIR) || state.getMaterial().isReplaceable());
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
                    BlockPos pos = center.offset(x, y, z);
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
        if(changedSet.contains(pos) || !canReplace(world,world,pos)) return;
        setBlockKnownShape(world,pos,state);
        changedSet.add(pos.immutable());
        boundingBox.expand(new MutableBoundingBox(pos.immutable(),pos.immutable()));
    }

    /**
     * 不检测就放置
     * @deprecated 特殊时候使用
     * */
    public void setBlockUnsecurity(IWorld world,BlockPos pos,BlockState state,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
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
        for (BlockPos pos : blockPos) {
            setBlock(world, pos, state, changedSet, boundingBox);
        }
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
    public EnumCornerDirection2D genRandomL(IWorld world, BlockPos center, BlockState state, int height, Random random, Set<BlockPos> changedSet, MutableBoundingBox boundingBox){
        Direction[] offsets = {random.nextBoolean()?Direction.EAST:Direction.WEST,
                                random.nextBoolean()?Direction.SOUTH:Direction.NORTH};
        EnumCornerDirection2D directionCorner2d = EnumCornerDirection2D.fromDirections(offsets[0],offsets[1]);
        genL(world,center,state,height,directionCorner2d,changedSet,boundingBox);
        return directionCorner2d;
    }



    /**
     * 按L字生成方块
     * */
    public void genL(IWorld world,BlockPos center,BlockState state,int height,EnumCornerDirection2D side,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        if(side == null) return;
        for(int y = 0; y < height;y++){
            setBlocks(world,state,changedSet,boundingBox,center.offset(0,y,0),
                    center.offset(side.getStepX(),y,0),
                    center.offset(0,y,side.getStepZ()));
        }
    }

    /**
     * 从中心生成正方形柱
     * @return 最后一次生成的位置的中心
     * */
    public BlockPos genRegCenter(IWorld world, BlockPos pos,BlockState state, int height, int radius, Set<BlockPos> changedSet, MutableBoundingBox boundingBox){
        BlockPos pos$Mutable = pos.mutable();
        for(int y = 0; y < height;y++){
            for(int x = -radius;x < radius;x++){
                for(int z = -radius;z < radius;z++){
                    setBlock(world,pos$Mutable.immutable().offset(x,0,y).immutable(),state,changedSet,boundingBox);
                }
            }
            pos$Mutable.above(1); //pos上移一格
        }
        return pos$Mutable.immutable();
    }

    /**
     * 中心往一个斜方向生成正方形柱,不支持在y轴生成正方形
     * @return 最后一次生成的位置的中心
     * */
    public BlockPos genReg(IWorld world,BlockPos pos,BlockState state,int height,int offset,EnumCornerDirection2D side,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        BlockPos pos$Mutable = pos.mutable();
        for(int y = 0; y < height;y++){
            for(int x = 0;x <= offset;x++){
                for(int z = 0;z <= offset;z++){
                    setBlock(world,pos$Mutable.immutable().offset(x * side.getStepX(),0,z * side.getStepZ()).immutable(),state,changedSet,boundingBox);
                }
            }
            pos$Mutable = pos$Mutable.above(1); //pos上移一格
        }
        return pos$Mutable.immutable().below();
    }

    public void genBlockWithCorner(IWorld world,BlockPos pos,BlockState state,int height,EnumCornerDirection2D side,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        for(int y = 0; y < height;y++){
            setBlock(world,pos.offset(y * side.getStepX(),y,y * side.getStepZ()),state,changedSet,boundingBox);
        }
    }

    public void genBlockWithDirection(IWorld world,BlockPos pos,BlockState state,int height,Direction side,Set<BlockPos> changedSet,MutableBoundingBox boundingBox){
        for(int y = 0; y < height;y++){
            setBlock(world,pos.offset(y * side.getStepX(),y,y * side.getStepZ()),state,changedSet,boundingBox);
        }
    }

    /**
     * 每节都会以1X2高度生成<br/>
     * 00X<br/>
     * 00X<br/>
     * 0X<br/>
     * 0X<br/>
     *X<br/>
     */
    public void genCurve(IWorld world, BlockPos pos,BlockState state,EnumCornerDirection2D side,int height, Set<BlockPos> changedSet, MutableBoundingBox box){
        BlockPos currentPos = pos;
        int y = 0;
        while(y < height){
            setBlock(world,pos,state,changedSet, box);
            y += 1;
            if(y!=0 && y % 2 == 0)  currentPos = currentPos.above();
            else currentPos = currentPos.offset(side.getStepX(),1,side.getStepZ());

        }
    }

    /**
     * 每节都会以1X2高度生成<br/>
     * 00X<br/>
     * 00X<br/>
     * 0X<br/>
     * 0X<br/>
     *X<br/>
     */
    public void genCurve(IWorld world, BlockPos pos,BlockState state,Direction side,int height, Set<BlockPos> changedSet, MutableBoundingBox box){
        BlockPos currentPos = pos.immutable();
        int y = 0;
        while(y < height){
            setBlock(world,currentPos.immutable(),state,changedSet, box);
            y += 1;
            if(y!=0 && y % 2 == 0)  currentPos = currentPos.above().immutable();
            else currentPos = currentPos.offset(side.getStepX(),1,side.getStepZ()).immutable();

        }
    }

    /**
     * 更新所有的树叶blockState,
     * 同时获得绑定盒中的所有放置的BlockPos并存入VoxelShapePart中
     * */
    protected VoxelShapePart updateLeaves(IWorld world, MutableBoundingBox boundingBox, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves) {
        List<Set<BlockPos>> list = Lists.newArrayList(); //新建一个ArrayList
        //把boundBox的x，y，z长度和深度构造一个BitSetVoxelShapePart,在内部会创建一个BitSe
        //BitSet的大小是boundBox的体积
        //BitSet储存的是在绑定盒范围内是否有该方块（有待验证），使用二进制进行保存,index为[(x * 绑定盒深度ySize + y) - 绑定盒纵深zSize + z]
        VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(boundingBox.getXSpan(), boundingBox.getYSpan(), boundingBox.getZSpan());
        int i = 6;
        //填充list
        for(int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        //遍历所有changeLeaves,如果叶子的BlockPos在绑定盒内部，更新bitMap的储存,
        // 并且储存 方块在绑定盒内部 距 绑定盒初始点 的 最小距离和最大距离
        for(BlockPos blockpos : Lists.newArrayList(changedLeaves)) {
            if (boundingBox.isInside(blockpos)) {
                voxelshapepart.setFull(blockpos.getX() - boundingBox.x0, blockpos.getY() - boundingBox.y0, blockpos.getZ() - boundingBox.z0, true, true);
            }
        }

        //同样的，对于原木也会改变储存，获得最小最大距离
        for(BlockPos blockpos1 : Lists.newArrayList(changedLogs)) {
            if (boundingBox.isInside(blockpos1)) {
                voxelshapepart.setFull(blockpos1.getX() - boundingBox.x0, blockpos1.getY() - boundingBox.y0, blockpos1.getZ() - boundingBox.z0, true, true);
            }

            //在每次更新完最大最小距离后，在四个方向上检测原木周围是否有树叶方块
            //如果有树叶方块则更新树叶的Properties（distance）
            // 注意：这是第一次更新，虽然遍历了所有树叶方块，但是这次树叶方块值会更新原木旁边distant为1的树叶的properties
            for(Direction direction : Direction.values()) {
                blockpos$mutable.setWithOffset(blockpos1, direction);
                if (!changedLogs.contains(blockpos$mutable)) {
                    BlockState blockstate = world.getBlockState(blockpos$mutable);
                    if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
                        list.get(0).add(blockpos$mutable.immutable()); //list加入该树叶方块
                        setBlockKnownShape(world, blockpos$mutable, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
                        //同样的更新一次
                        if (boundingBox.isInside(blockpos$mutable)) {
                            voxelshapepart.setFull(blockpos$mutable.getX() - boundingBox.x0, blockpos$mutable.getY() - boundingBox.y0, blockpos$mutable.getZ() - boundingBox.z0, true, true);
                        }
                    }
                }
            }
        }

        //遍历5次（因为最大distant为7），每次获取distant为[l-1]的set和distant为[l]的set，然后更新树叶的distance
        for(int l = 1; l < 6; ++l) {
            Set<BlockPos> set = list.get(l - 1);
            Set<BlockPos> set1 = list.get(l);

            //同样的，更新和计算最大和最小
            for(BlockPos blockpos2 : set) {
                if (boundingBox.isInside(blockpos2)) {
                    voxelshapepart.setFull(blockpos2.getX() - boundingBox.x0, blockpos2.getY() - boundingBox.y0, blockpos2.getZ() - boundingBox.z0, true, true);
                }

                //然后更新distant
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

        //返回voxelshapepart
        return voxelshapepart;
    }

    /**判定是否有面积生长该植物，请在doPlace()使用该方法*/
    public boolean check(IWorld reader, BlockPos pos,int height,int radius){
        for(int y = 0; y <= height; y++){
            for(int x = -radius; x <= radius; x++){
                for(int z = -radius; z <= radius; z++){
                    BlockPos offsetPos = pos.offset(x,y,z);
                    if(offsetPos.getY() >= 255 || !canReplace(reader,reader,offsetPos)) return false;
                }
            }
        }
        return true;
    }


}
