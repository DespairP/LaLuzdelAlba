package teamHTBP.LaLuzdelAlba.Blocks.Environment.Plants;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class BlockLaLuzSapling extends SaplingBlock {
    ITreeGetter getter;

    public BlockLaLuzSapling(ITreeGetter getter) {
        super(getter.getTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }

    public interface ITreeGetter{
        public abstract Tree getTree();
    }
}
