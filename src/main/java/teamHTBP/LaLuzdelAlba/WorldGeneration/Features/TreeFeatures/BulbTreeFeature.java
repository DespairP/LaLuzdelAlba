package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;
import teamHTBP.LaLuzdelAlba.Utils.EnumCornerDirection2D;

import java.util.Random;
import java.util.Set;
import static teamHTBP.LaLuzdelAlba.Utils.RandomUtils.*;

/**生成类似于bulb形状的树*/
public class BulbTreeFeature extends BasicTreeFeature{
    private BlockPos endTrunk;
    Direction direction;

    public BulbTreeFeature(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock, int minHeight, int maxHeight) {
        super(leavesBlock, logBlock,vineBlock, minHeight, maxHeight);
    }

    @Override
    public boolean doPlace(ISeedReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config) {
        //取自BOP代码，一直移动直到有地面的地方来生成该树
        while (pos.getY() > 1 && (reader.isEmptyBlock(pos) || reader.getBlockState(pos).getMaterial() == Material.AIR)) pos = pos.below();

        int height = nextIntBetween(random,minHeight,maxHeight);

        if(!check(reader,pos.above(), height - 4,1) || !check(reader,pos.above(height - 3),4,4)) return false;

        int chunkHeight = (int)Math.floor(height / 2.0f);
        int branchHeight = (int)Math.floor((height - chunkHeight) / 2.0f);
        generateChunk(reader,pos.above(),chunkHeight,changedLogs,boundingBox);
        generateBranch(reader,random,branchHeight,changedLogs,boundingBox);
        generateLeaves(reader,random,height - chunkHeight,changedLeaves,boundingBox);

        VoxelShapePart voxelShapePart = updateLeaves(reader,boundingBox,changedLogs,changedLeaves);
        Template.updateShapeAtEdge(reader,3,voxelShapePart,boundingBox.x0,boundingBox.y0,boundingBox.z0);
        return true;
    }

    public void generateChunk(IWorld world, BlockPos pos,int chunkHeight,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        //生成树干，随机方向即可
        endTrunk = genReg(world,pos,logBlock,chunkHeight,0, EnumCornerDirection2D.getRandom(),changedLogs,boundingBox);
        if(logBlock.hasProperty(BlockStateProperties.AXIS)){ setBlockWithDirections(world, endTrunk, logBlock, changedLogs, boundingBox); return;}
        setBlocks(world,logBlock,changedLogs, boundingBox,endTrunk.east(),endTrunk.west(),endTrunk.north(),endTrunk.south());
    }

    public void generateBranch(IWorld world,Random random,int branchHeight,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        if(endTrunk == null) return;
        //随机选择方向并生成树枝
        direction = Direction.getRandom(random);
        Direction directionFlip = direction.getOpposite();
        for(int i = 0;i < branchHeight;i++) {
            //方向上的树枝为最高树枝，反方向树枝高度 = 树枝高度 - 1
            setLogBlock(world,endTrunk.above(1 + i).offset(direction.getNormal()), logBlock, changedLogs, boundingBox);
            if(i >= branchHeight - 1) continue;
            setLogBlock(world,endTrunk.above(1 + i).offset(directionFlip.getNormal()), logBlock, changedLogs, boundingBox);
        }
    }

    public void generateLeaves(IWorld world,Random random,int leavesHeight,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox){
        //随机生成树叶...
        int flipLine = leavesHeight / 2;
        BlockPos pos = endTrunk;
        int currentX = 2 + direction.getStepX(),currentZ = 2 + direction.getStepZ();
        for(int i = 0; i <= leavesHeight;i++){
            if(i > flipLine){
                currentX -=1;
                generateCircle(world,pos,leavesBlock,1, currentX,changedLeaves,boundingBox);
                boolean shouldMove = random.nextBoolean();
                pos = pos.above();
                continue;
            }
            currentX += 1;
            currentZ += 1;
            placeLeavesRowRandom(world,pos,random,1,currentX - 1 , currentZ - 1,changedLeaves,boundingBox,true);
            //changePosition
            boolean shouldMove = random.nextBoolean();
            pos = pos.above();
        }
    }

    /**Builder*/
    public static class BulbTreeBuilder extends Builder<BulbTreeBuilder, BulbTreeFeature>{
        public BulbTreeBuilder(BlockState leavesBlock, BlockState logBlock,int minHeight,int maxHeight) {
            super(leavesBlock, logBlock,minHeight,maxHeight);
        }

        @Override
        public BulbTreeFeature build() {
            return new BulbTreeFeature(leavesBlock,logBlock,vineBlock,minHeight,maxHeight);
        }
    }

    /**
     * 以
     * 0x0
     * x0x
     * 0x0
     * 放置方块
     * */
    private void setBlockWithDirections(IWorld world,BlockPos pos,BlockState state,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        for(Direction direction:Direction.values()) {
            if(direction.getAxis() == Direction.Axis.Y) continue;
            setBlockWithProperty(world, pos, state, direction, changedLogs, boundingBox);
        }
    }

    /**带property放置方块*/
    private void setBlockWithProperty(IWorld world,BlockPos pos,BlockState state,Direction side,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        setBlocks(world,state.setValue(BlockStateProperties.AXIS,side.getAxis()),changedLogs,boundingBox,pos.offset(side.getStepX(),0,side.getStepZ()));
    }

    public void placeLeavesRowRandom(IWorld world,BlockPos pos,Random random,int leavesHeight,int xOffset,int zOffset,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox,boolean detectBelow){
        for(int x = - xOffset; x < xOffset;x++){
            for(int z = -zOffset; z < zOffset;z++){
                BlockPos blockPos = pos.offset(x,0,z);
                if(!world.isEmptyBlock(blockPos)) continue;
                if((x == xOffset - 1 && z == -zOffset) || (x == xOffset - 1 && z == zOffset - 1) || (x == -xOffset && z == zOffset - 1) || (x == -xOffset && z == -zOffset)) continue;
                if((x == xOffset - 1 || 0 == zOffset - 1 || x == -xOffset || z == -zOffset) && random.nextInt(11) < 6) continue;
                setLeavesBlock(world,blockPos,leavesBlock,changedLeaves,boundingBox);
            }
        }
    }
}
