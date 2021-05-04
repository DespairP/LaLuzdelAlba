package teamHTBP.LaLuzdelAlba.Blocks.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractBushBlock extends BushBlock {
    /**能种植该植物的方块*/
    private List<Block> placeBlock;
    /**是否开启严格检查模式，如果开启，作物就只能种植到给定的list中，而不往父类继续判定*/
    private boolean strictMode = false;

    /**默认的构造方法，通过property进行构建，不开启严格检查模式且能种植在父类指定的方块上*/
    public AbstractBushBlock(Properties properties) {
        super(properties);
    }

    /**传入能种植该植物的方块的List,并且设定是否开启严格模式*/
    public AbstractBushBlock(Properties properties,List<Block> placeBlock,boolean strictMode) {
        super(properties);
        this.placeBlock = placeBlock;
        this.strictMode = strictMode;
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, IBlockReader reader, BlockPos pos) {
        Optional<List<Block>> blocksPlaceOn = Optional.ofNullable(placeBlock);
        AtomicBoolean canPlantOn = new AtomicBoolean(false);
        blocksPlaceOn.ifPresent(blocks -> {
            blocks.forEach(block ->{
                if(blockState.is(block)) canPlantOn.set(true);
            });
        });
        if(!strictMode && !canPlantOn.get()) return super.mayPlaceOn(blockState, reader, pos);
        return canPlantOn.get();
    }
}
