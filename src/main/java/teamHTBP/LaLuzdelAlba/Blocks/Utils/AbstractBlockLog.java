package teamHTBP.LaLuzdelAlba.Blocks.Utils;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**用于创造一个像原木一样转动的方块*/
public abstract class AbstractBlockLog extends RotatedPillarBlock {
    /**传入properties来构造方块*/
    public AbstractBlockLog(Properties properties) {
        super(properties);
    }

    /**使用默认构造方法*/
    public AbstractBlockLog(){
        super(Properties.of(Material.WOOD).strength(2.0f).sound(SoundType.WOOD));
    }



}
