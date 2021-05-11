package teamHTBP.LaLuzdelAlba.Items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.ItemGroups.ItemGroupsLoader;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

import static teamHTBP.LaLuzdelAlba.ItemGroups.ItemGroupsLoader.TAB_ENVIRONMENT;

public class ItemBlocksLoader {
    public static final DeferredRegister<Item> ITEMBLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, LaLuzdelAlba.MODID);
    private static Item.Properties BASIC_PROPERTY = new Item.Properties().tab(TAB_ENVIRONMENT);

    public ItemBlocksLoader(){
        ITEMBLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> SMOKY_LOG = ITEMBLOCKS.register("smoky_log", () -> new BlockItem(BlocksLoader.SMOKY_LOG.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> FUNGUS_SMOKY_LOG = ITEMBLOCKS.register("fungus_smoky_log", () -> new BlockItem(BlocksLoader.FUNGUS_SMOKY_LOG.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> MARSH_SMOKY_LOG = ITEMBLOCKS.register("marsh_smoky_log", () -> new BlockItem(BlocksLoader.MARSH_SMOKY_LOG.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> SPIRAL_SMOKY_LOG = ITEMBLOCKS.register("spiral_smoky_log", () -> new BlockItem(BlocksLoader.SPIRAL_SMOKY_LOG.get(),BASIC_PROPERTY));


    public static final RegistryObject<Item> SMOKY_LEAVES = ITEMBLOCKS.register("smoky_leaves", () -> new BlockItem(BlocksLoader.SMOKY_LEAVES.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> SPIRAL_LEAVES = ITEMBLOCKS.register("spiral_smoky_leaves", () -> new BlockItem(BlocksLoader.SPIRAL_LEAVES.get(),BASIC_PROPERTY));

    public static final RegistryObject<Item> SMOKY_DIRT = ITEMBLOCKS.register("smoky_dirt", ()->new BlockItem(BlocksLoader.SMOKY_DIRT.get(),BASIC_PROPERTY));

    public static final RegistryObject<Item> SMOKY_SAPLING = ITEMBLOCKS.register("smoky_sapling", ()->new BlockItem(BlocksLoader.SMOKY_SAPLING.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> MARSH_SMOKY_SAPLING = ITEMBLOCKS.register("marsh_smoky_sapling", ()->new BlockItem(BlocksLoader.MARSH_SMOKY_SAPLING.get(),BASIC_PROPERTY));
    public static final RegistryObject<Item> NORMAL_SMOKY_SAPLING = ITEMBLOCKS.register("normal_smoky_sapling", ()->new BlockItem(BlocksLoader.NORMAL_SMOKY_SAPLING.get(),BASIC_PROPERTY));

}
