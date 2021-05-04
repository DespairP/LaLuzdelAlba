package teamHTBP.LaLuzdelAlba.ItemGroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.Items.ItemBlocksLoader;

public class ItemGroupsLoader {
    public final static ItemGroup TAB_ENVIRONMENT = new BasicItemGroup("LaLuzdelAlba Environment", ()->new ItemStack( ItemBlocksLoader.SMOKY_LOG.get()));
}
