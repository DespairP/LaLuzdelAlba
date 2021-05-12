package teamHTBP.LaLuzdelAlba.Renders;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamHTBP.LaLuzdelAlba.Items.ItemBlocksLoader;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ItemColorHandlers {
    @SubscribeEvent
    public static void addBlockColor(ColorHandlerEvent.Item event){
        BlockColors colors = event.getBlockColors();
        event.getItemColors().register((itemstack,var_2)->{
            BlockState state = ((BlockItem)itemstack.getItem()).getBlock().defaultBlockState();
            return colors.getColor(state,null,null,var_2);
        }, ItemBlocksLoader.SMOKY_GRASS.get());
    }
}
