package teamHTBP.LaLuzdelAlba.Blocks.Environment;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSmokyGrass extends Block {
    public BlockSmokyGrass() {
        super(AbstractBlock.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    }



}
