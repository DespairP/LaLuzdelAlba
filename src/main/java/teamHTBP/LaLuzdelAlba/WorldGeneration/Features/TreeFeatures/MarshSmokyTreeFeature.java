package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.template.Template;
import teamHTBP.LaLuzdelAlba.Utils.EnumCornerDirection2D;

import java.util.Random;
import java.util.Set;

import static teamHTBP.LaLuzdelAlba.Utils.DirectionUtils.flip;
import static teamHTBP.LaLuzdelAlba.Utils.RandomUtils.*;

/**熏沼树Feature*/
public class MarshSmokyTreeFeature extends BasicTreeFeature{
    /**生成树枝最大高度*/
    private int branchMaxHeight;
    /**生成树枝最小高度*/
    private int branchMinHeight;
    /**树枝的宽度*/
    private int branchWidth;
    /**树的原木的其他品种*/
    private BlockState altLog;
    /**树干末枝*/
    private BlockPos endTrunk;
    /**树干生成方块*/
    private EnumCornerDirection2D randomSide;


    public MarshSmokyTreeFeature(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock, int minHeight, int maxHeight, int branchMaxHeight, int branchMinHeight, int branchWidth, BlockState altLog) {
        super(leavesBlock, logBlock, vineBlock, minHeight, maxHeight);
        this.branchMaxHeight = branchMaxHeight;
        this.branchMinHeight = branchMinHeight;
        this.branchWidth = branchWidth;
        this.altLog = altLog;
    }

    @Override
    public boolean doPlace(ISeedReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config) {
        //取自BOP代码，一直移动直到有地面的地方来生成该树
        while (pos.getY() > 1 && (reader.isEmptyBlock(pos) || reader.getBlockState(pos).getMaterial() == Material.AIR)) pos = pos.below();

        int chunkHeight = nextIntBetween(random,minHeight,maxHeight);
        int branchHeight = nextIntBetween(random,branchMinHeight,branchMaxHeight);
        if(!checkPlace(reader,pos,chunkHeight,branchHeight)) return false;

        generateChunk(reader,random,pos,chunkHeight,changedLogs,boundingBox);
        generateBranch(reader,random,branchHeight,changedLogs,changedLeaves,boundingBox);
        generateLeaves(reader,pos,random,chunkHeight,changedLeaves,boundingBox);
        generateRoot(reader,pos.immutable(),random,chunkHeight,changedLogs,boundingBox);

        VoxelShapePart voxelShapePart = updateLeaves(reader,boundingBox,changedLogs,changedLeaves);
        Template.updateShapeAtEdge(reader,3,voxelShapePart,boundingBox.x0,boundingBox.y0,boundingBox.z0);
        return true;
    }

    public void generateChunk(IWorld world,Random random, BlockPos pos,int chunkHeight,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        //生成树干，会保存树干生成的方向 和 最后树干生成的中心
        randomSide = EnumCornerDirection2D.getRandom();
        endTrunk = genReg(world,pos.above(1),logBlock,chunkHeight,1,randomSide,changedLogs,boundingBox);
    }

    public void generateBranch(IWorld world,Random random,int branchHeight,Set<BlockPos> changedLogs,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox){
        BlockPos pos = endTrunk;
        //生成最低一节的树干，树干的第一层会生成树丛，最低一节按树干生成方向生成
        genBlockWithCorner(world,pos.offset(randomSide.getStepX(),0 , randomSide.getStepZ()),logBlock,branchMinHeight,randomSide,changedLogs,boundingBox);
        setLogBlock(world,pos.offset(2 *randomSide.getStepX(),0 ,2 * randomSide.getStepZ()), logBlock,changedLogs,boundingBox);
        generateBush(world,pos.offset(2 *randomSide.getStepX(),1 ,2 * randomSide.getStepZ()), randomSide, random, changedLogs,changedLeaves,boundingBox,false);

        //生成最高一节树枝，树枝树干反方向角的x轴生成
        Direction direction = randomSide.getFlip().getxDirection();
        BlockPos posBranch =genReg(world,pos.above(),logBlock,2,0,randomSide,changedLogs,boundingBox);
        genCurve(world,posBranch.offset(direction.getStepX(),-1,direction.getStepZ()),logBlock,direction,branchHeight,changedLogs,boundingBox);
        generateBush(world,pos.offset(2 * direction.getStepX() , 3 ,2 * direction.getStepZ()),randomSide.getFlip(), random, changedLogs, changedLeaves, boundingBox,false);

        //生成最高一节旁边一节的树枝，首先在最高位置的生成的反方向生成两个木桩,生成方向为树干反方向角的z轴,然后生成特殊形状的叶子
        Direction flipDirection = flip(direction);
        Direction glowDirection = randomSide.getFlip().getzDirection();
        BlockPos pos$0 = pos.offset(flipDirection.getStepX(),0,flipDirection.getStepZ());
        BlockPos posBranch$2 =genReg(world,pos$0.above(),logBlock,2,0,randomSide,changedLogs,boundingBox);
        genCurve(world,posBranch$2.offset(glowDirection.getStepX(),0,glowDirection.getStepZ()),logBlock,glowDirection,branchHeight - 1,changedLogs,boundingBox);
        generateBush(world,posBranch$2.offset(glowDirection.getStepX() * 2,0,glowDirection.getStepZ() * 2),glowDirection,changedLeaves,boundingBox);

        //最后生成斜树枝，从最低点按z轴移动一格，得到基础格，然后进行生成，生成方向是树干生成方向的Z轴镜面翻转
        EnumCornerDirection2D glowDirection$corner = randomSide.getMirror(Direction.Axis.Z);
        pos = pos.offset(randomSide.getStepX(),0 , randomSide.getStepZ()).immutable().offset(randomSide.getFlip().getStepX(), 0, 0);
        BlockPos posBranch$3 =genReg(world,pos.above(),logBlock,1,0,randomSide,changedLogs,boundingBox);
        genReg(world,pos.offset(glowDirection$corner.getStepX(),0,glowDirection$corner.getStepZ()),logBlock,2,0,randomSide,changedLogs,boundingBox);
        generateBush(world,pos.offset(glowDirection$corner.getStepX(),0,glowDirection$corner.getStepZ() * 2),glowDirection$corner.getzDirection(),changedLeaves,boundingBox);
        setLogBlock(world,pos.offset(glowDirection$corner.getStepX(),2,glowDirection$corner.getStepZ() * 2),logBlock,changedLogs,boundingBox);
    }

    public void generateLeaves(IWorld world,BlockPos pos,Random random,int chunkHeight,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox){
        int generateHeight = nextIntBetween(random,3,chunkHeight - 1);
        EnumCornerDirection2D direction2D = randomSide.getFlip();
        genL(world,pos.offset(direction2D.getStepX(),generateHeight,direction2D.getStepZ()),leavesBlock,1,randomSide,changedLeaves,boundingBox);
        genL(world,pos.offset(randomSide.getStepX() * 2,generateHeight + 1,randomSide.getStepZ() * 2),leavesBlock,1,direction2D,changedLeaves,boundingBox);

    }

    public void generateRoot(IWorld world,BlockPos pos,Random random,int chunkHeight,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        EnumCornerDirection2D direction2D = randomSide.getFlip();
        generateRandomly(world,pos.offset(direction2D.getStepX(),-1,0),random,randomSide.getzDirection(),3,2,changedLogs,boundingBox);
        generateRandomly(world,pos.offset(0,-1,direction2D.getStepZ()),random,randomSide.getxDirection(),3,2,changedLogs,boundingBox);
        generateRandomly(world,pos.offset(randomSide.getStepX() * 2,-1,0),random,randomSide.getzDirection(),3,2,changedLogs,boundingBox);
        generateRandomly(world,pos.offset(0,-1,randomSide.getStepZ() * 2),random,randomSide.getxDirection(),3,2,changedLogs,boundingBox);
    }



    /**Builder*/
    public static class SmokyMarshTreeFeatureBuilder extends Builder<SmokyMarshTreeFeatureBuilder, MarshSmokyTreeFeature>{
        /**生成树枝最大高度*/
        private int branchMaxHeight;
        /**生成树枝最小高度*/
        private int branchMinHeight;
        /**生成树枝的宽度*/
        private int branchWidth;
        /**设置另外一种品种*/
        private BlockState altLog;

        public SmokyMarshTreeFeatureBuilder(BlockState leavesBlock, BlockState logBlock,int minHeight,int maxHeight) {
            super(leavesBlock, logBlock);
            this.branchMaxHeight = 5;
            this.branchMinHeight = 3;
            this.altLog = logBlock;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        public SmokyMarshTreeFeatureBuilder setBranchMaxHeight(int branchMaxHeight){
            this.branchMaxHeight = branchMaxHeight;
            return this;
        }

        public SmokyMarshTreeFeatureBuilder setBranchMinHeight(int branchMinHeight){
            this.branchMinHeight = branchMinHeight;
            return this;
        }

        public SmokyMarshTreeFeatureBuilder setAltLog(BlockState altLog){
            this.altLog = altLog;
            return this;
        }

        @Override
        public MarshSmokyTreeFeature build() {
            return new MarshSmokyTreeFeature(leavesBlock,
                    logBlock,
                    Blocks.AIR.defaultBlockState(),
                    minHeight,
                    maxHeight,
                    branchMaxHeight,
                    branchMinHeight,
                    branchWidth,
                    altLog);
        }
    }

    /**生成中间原木四周是树叶的结构*/
    public void generateBush(IWorld world, BlockPos pos,Random random, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox box){
        setLogBlock(world,pos,logBlock,changedLogs,box);
        for(Direction direction:Direction.values()) {
            setBlocks(world, leavesBlock, changedLeaves, box, pos.offset(direction.getStepX(), 0, direction.getStepZ()));
        }
    }

    /**生成中间原木四周是树叶的结构*/
    public void generateBush(IWorld world, BlockPos pos,EnumCornerDirection2D side,Random random, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox box,boolean generateTop){
        setLogBlock(world,pos,logBlock,changedLogs,box); //首先在中心生成原木方块
        for(Direction direction:Direction.values()) {
            if(direction == Direction.DOWN) continue; //下面不生成方块

            if(random.nextBoolean())
                setLeavesBlock(world,pos.offset(direction.getStepX(),direction.getStepY(),direction.getStepZ()),leavesBlock,changedLeaves,box);

            if(random.nextInt(10) < 8 && direction != Direction.UP)
                setBlocks(world, leavesBlock, changedLeaves, box, pos.offset(direction.getStepX(), 0, direction.getStepZ()));
        }
        //最后生成叶尖
        if(generateTop){
            Direction direction = nextDirection(random,side);
            setLeavesBlock(world,pos.offset(2 * direction.getStepX(),0,2 * direction.getStepZ()),leavesBlock,changedLeaves,box);
        }
        setLeavesBlock(world,pos.offset(side.getStepX(),0, side.getStepZ()),leavesBlock,changedLeaves,box);
    }


    /**生成树叶的结构
     * XX
     * 01X
     * XX
     * */
    public void generateBush(IWorld world, BlockPos pos,Direction side, Set<BlockPos> changedLeaves, MutableBoundingBox box){
        setBlocks(world,leavesBlock,changedLeaves,box,
                pos,
                pos.offset(side.getStepX(),0,side.getStepZ()),
                pos.offset(side.getClockWise().getStepX(),0,side.getClockWise().getStepZ()),
                pos.offset(side.getCounterClockWise().getStepX(),0,side.getCounterClockWise().getStepZ()),
                pos.offset(EnumCornerDirection2D.fromDirections(flip(side),side.getClockWise()).getStepX(),0,EnumCornerDirection2D.fromDirections(flip(side),side.getClockWise()).getStepZ()),
                pos.offset(EnumCornerDirection2D.fromDirections(flip(side),side.getCounterClockWise()).getStepX(),0,EnumCornerDirection2D.fromDirections(flip(side),side.getCounterClockWise()).getStepZ())
                );
    }

    /**检查是否能够摆放*/
    public boolean checkPlace(IWorld reader, BlockPos pos, int chunkHeight,int branchHeight){
        return checkBase(reader,pos,1,4)
                && check(reader,pos.above(),chunkHeight,2)
                && check(reader,pos.above(chunkHeight + 2),branchHeight,4);
    }

    /**检查基底是否符合条件,不同于普通check,因为基底会嵌入到地面中,检测覆盖的地方是否有不可替换的方块*/
    public boolean checkBase(IWorld reader, BlockPos pos, int height, int radius) {
        for(int y = 0; y <= height; y++){
            for(int x = -radius; x <= radius; x++){
                for(int z = -radius; z <= radius; z++){
                    BlockPos offsetPos = pos.offset(x,y,z).immutable();
                    if(offsetPos.getY() >= 255 || !(isGrassBlockorDirt(reader,offsetPos) || canReplace(reader,reader,offsetPos))) return false;
                }
            }
        }
        return true;
    }

    public void generateRandomly(IWorld world, BlockPos pos,Random random,Direction side,int height,int offset, Set<BlockPos> changedLogs, MutableBoundingBox box){
        for(int o = 0 ; o < offset;o++) {
           for (int y = random.nextBoolean()?0:1; y <= height; y++) {
               if(random.nextInt(5) < 2) break;
               setBlockUnsecurity(world,pos.offset(side.getStepX() * o, y , side.getStepZ() * o),logBlock,changedLogs,box);
           }
        }
    }
}
