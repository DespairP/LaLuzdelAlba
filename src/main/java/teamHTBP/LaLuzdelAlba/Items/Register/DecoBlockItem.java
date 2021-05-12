package teamHTBP.LaLuzdelAlba.Items.Register;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

public class DecoBlockItem extends BlockItem {
    public DecoBlockItem(Block block, Properties properties,String registerName) {
        super(block, properties);
        this.setRegistryName(LaLuzdelAlba.MODID,registerName);
    }
}
