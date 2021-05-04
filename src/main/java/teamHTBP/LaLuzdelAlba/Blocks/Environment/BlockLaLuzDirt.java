package teamHTBP.LaLuzdelAlba.Blocks.Environment;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockLaLuzDirt extends Block {
    public BlockLaLuzDirt() {
        super(AbstractBlock.Properties.of(Material.DIRT).strength(0.5F).sound(SoundType.GRAVEL));
    }
}
