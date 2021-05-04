package teamHTBP.LaLuzdelAlba.Blocks.Environment;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockLaLuzLeaves extends Block{
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;


    /**传入properties构筑方块*/
    public BlockLaLuzLeaves(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, Integer.valueOf(7)).setValue(PERSISTENT, Boolean.valueOf(false)));
    }

    /**默认无参,自动构造树叶的基础Properties*/
    public BlockLaLuzLeaves(){
        this(AbstractBlock.Properties.of(Material.LEAVES)
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isSuffocating((var_1,var_2,var_3)->false)
                .strength(0.2f)
                .isViewBlocking((var_1,var_2,var_3)->false)

        );
    }

    public int getLightBlock(BlockState blockState, IBlockReader reader, BlockPos pos) {
        return 1;
    }

    public VoxelShape getBlockSupportShape(BlockState blockState, IBlockReader blockReader, BlockPos pos) {
        return VoxelShapes.empty();
    }

    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(DISTANCE) == 7 && !blockState.getValue(PERSISTENT);
    }

    public void randomTick(BlockState blockState, ServerWorld world, BlockPos pos, Random random) {
        if (!blockState.getValue(PERSISTENT) && blockState.getValue(DISTANCE) == 7) {
            dropResources(blockState, world, pos);
            world.removeBlock(pos, false);
        }
    }

    public void tick(BlockState blockState, ServerWorld world, BlockPos pos, Random p_225534_4_) {
        world.setBlock(pos, updateDistance(blockState, world, pos), 3);
    }

    private static BlockState updateDistance(BlockState blockState, IWorld world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        
        for(Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(pos, direction);
            i = Math.min(i, getDistanceAt(world.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }
        return blockState.setValue(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState blockState) {
        if (BlockTags.LOGS.contains(blockState.getBlock())) {
            return 0;
        } else {
            return blockState.getBlock() instanceof BlockLaLuzLeaves ? blockState.getValue(DISTANCE) : 7;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)), context.getLevel(), context.getClickedPos());
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos pos, Random random) {
        if (world.isRainingAt(pos.above())) {
            if (random.nextInt(15) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = world.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(world, blockpos, Direction.UP)) {
                    double d0 = (double)pos.getX() + random.nextDouble();
                    double d1 = (double)pos.getY() - 0.05D;
                    double d2 = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
