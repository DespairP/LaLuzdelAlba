package teamHTBP.LaLuzdelAlba.WorldGeneration.Features.TreeFeatures;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.template.Template;
import teamHTBP.LaLuzdelAlba.Utils.RandomUtils;

import java.util.Random;
import java.util.Set;

import static teamHTBP.LaLuzdelAlba.Utils.RandomUtils.*;
import static teamHTBP.LaLuzdelAlba.Utils.DirectionUtils.*;

/**低矮灌木树Feature*/
public class LowShrubsFeature extends BasicTreeFeature{
    /**灌木丛*/
    protected final int foliageNumber;
    /**放置原木的side*/
    private Direction side[];

    public LowShrubsFeature(BlockState leavesBlock, BlockState logBlock, BlockState vineBlock, int minHeight, int maxHeight,int foliageNumber) {
        super(leavesBlock, logBlock, vineBlock, minHeight, maxHeight);
        this.foliageNumber = foliageNumber;
    }


    @Override
    public boolean doPlace(ISeedReader reader, Random random, BlockPos pos, Set<BlockPos> changedLogs, Set<BlockPos> changedLeaves, MutableBoundingBox boundingBox, BaseTreeFeatureConfig config) {
        //取自BOP代码，一直移动直到有地面的地方来生成该树
        while (pos.getY() > 1 && (reader.isEmptyBlock(pos) || reader.getBlockState(pos).getMaterial() == Material.AIR)) pos = pos.below();
        if(!canPlantOn(reader,pos)) return false;

        final int height = nextIntBetween(random,minHeight,maxHeight);

        //确认是否有空间生成该树
        if(!check(reader,pos.above(),height,1)) return false;

        //生成树干
        generateChunks(reader,pos,random,height,changedLogs,boundingBox);

        //生成树叶
        generateLeaves(reader,pos,random,height,changedLeaves,boundingBox);

        //获取VoxelShapePart，并更新
        VoxelShapePart voxelShapePart = updateLeaves(reader,boundingBox,changedLogs,changedLeaves);
        Template.updateShapeAtEdge(reader,3,voxelShapePart,boundingBox.x0,boundingBox.y0,boundingBox.z0);
        return true;
    }

    /**生成树干*/
    public void generateChunks(IWorld world,BlockPos pos,Random random,int height,Set<BlockPos> changedLogs,MutableBoundingBox boundingBox){
        int baseHeight = height - minHeight + 1;
        generateCircle(world,pos.above(),logBlock, baseHeight,1, changedLogs,boundingBox);
        Direction[] side = genRandomL(world, pos.offset(0,baseHeight + 1,0),logBlock,2,random,changedLogs,boundingBox);
        setLogBlock(world,pos.offset(0, height,0),logBlock,changedLogs,boundingBox);
        this.side = side;
    }

    /**生成树叶*/
    public void generateLeaves(IWorld world,BlockPos pos,Random random,int height,Set<BlockPos> changedLeaves,MutableBoundingBox boundingBox){
        int generatedLeaves = 0;
        //如果放置过原木,会产生side，在side的对面生成树叶
        if(side != null && side.length == 2) {
            Direction[] flipDirection=flipDirections(side);
            System.out.println(flipDirection[0]+ " " +flipDirection[1]);
            genL(world,
                 pos.offset(flipDirection[0].getStepX(),nextIntBetween(random,height - minHeight + 2 , height - 1), flipDirection[1].getStepZ()).immutable(),
                 leavesBlock, 1, side, changedLeaves,boundingBox);
            //生成底层树叶
            Direction baseDirection = nextDirection(random,side[0],side[1]);
            setBlocks(world,leavesBlock,changedLeaves, boundingBox,pos.offset(baseDirection.getStepX(),1 ,baseDirection.getStepZ()),
                                                                   pos.offset(side[0].getStepX() + baseDirection.getStepX(),1 ,baseDirection.getStepZ()),
                                                                   pos.offset(baseDirection.getStepX(),1 ,baseDirection.getStepZ() + side[1].getStepZ()));
            generatedLeaves = 5;
            if(generatedLeaves < foliageNumber) return;
        }
        //如果没有放置过原木,会随机进行放置
        for(;generatedLeaves < foliageNumber;generatedLeaves++)
            setLeavesBlock(
                    world,
                    pos.offset(nextIntBetween(random,-2,2),nextIntBetween(random,0,height),nextIntBetween(random,-2,2)),
                    leavesBlock,
                    changedLeaves,
                    boundingBox);
    }

    /**确认是否有空间生成树*/
    @Override
    public boolean check(IWorld reader, BlockPos pos, int height, int radius) {
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
