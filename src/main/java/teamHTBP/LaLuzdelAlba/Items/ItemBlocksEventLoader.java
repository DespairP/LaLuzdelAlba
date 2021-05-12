package teamHTBP.LaLuzdelAlba.Items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksEventLoader;
import teamHTBP.LaLuzdelAlba.Items.Register.DecoBlockItem;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

@Mod.EventBusSubscriber(modid = LaLuzdelAlba.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemBlocksEventLoader {

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        BlocksEventLoader.event_Registered_Block.forEach((id,block)->{
            event.getRegistry().register(new DecoBlockItem(block,ItemBlocksLoader.BASIC_PROPERTY,id));
        });

    }
}
