package teamHTBP.LaLuzdelAlba.Blocks.Decoration;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

public class BlockLaLuzPlank extends Block {
    public BlockLaLuzPlank() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    public BlockLaLuzPlank(String registerName) {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        this.setRegistryName(LaLuzdelAlba.MODID,registerName);
    }
}
