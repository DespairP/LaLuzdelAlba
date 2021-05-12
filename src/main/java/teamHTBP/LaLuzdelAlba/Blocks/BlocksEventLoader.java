package teamHTBP.LaLuzdelAlba.Blocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamHTBP.LaLuzdelAlba.Blocks.Decoration.BlockLaLuzPlank;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 使用EventLoader主要用于专门批量注册装饰性方块
 * */
@Mod.EventBusSubscriber(modid = LaLuzdelAlba.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksEventLoader {
    public static Map<String,Block> event_Registered_Block = new LinkedHashMap<>();

    static {
        event_Registered_Block.put("deco_0_smoky_planks",new BlockLaLuzPlank("deco_0_smoky_planks"));
        event_Registered_Block.put("deco_1_smoky_planks",new BlockLaLuzPlank("deco_1_smoky_planks"));
        event_Registered_Block.put("deco_2_smoky_planks",new BlockLaLuzPlank("deco_2_smoky_planks"));
        event_Registered_Block.put("deco_0_spiral_smoky_planks",new BlockLaLuzPlank("deco_0_spiral_smoky_planks"));
        event_Registered_Block.put("deco_1_spiral_smoky_planks",new BlockLaLuzPlank("deco_1_spiral_smoky_planks"));
        event_Registered_Block.put("deco_2_spiral_smoky_planks",new BlockLaLuzPlank("deco_2_spiral_smoky_planks"));
        event_Registered_Block.put("deco_0_marsh_smoky_planks",new BlockLaLuzPlank("deco_0_marsh_smoky_planks"));
        event_Registered_Block.put("deco_1_marsh_smoky_planks",new BlockLaLuzPlank("deco_1_marsh_smoky_planks"));
        event_Registered_Block.put("deco_2_marsh_smoky_planks",new BlockLaLuzPlank("deco_2_marsh_smoky_planks"));

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event_Registered_Block.forEach(
                (id,block)->event.getRegistry().register(block)
        );
    }

    /**返回注册的装饰性方块*/
    public Optional<Block> getDecoBlock(String decoBlockId){
        return Optional.ofNullable(event_Registered_Block.get(decoBlockId));
    }


}
