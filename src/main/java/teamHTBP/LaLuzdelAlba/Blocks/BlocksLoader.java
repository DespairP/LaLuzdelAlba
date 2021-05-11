package teamHTBP.LaLuzdelAlba.Blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.BlockLaLuzDirt;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.BlockLaLuzLeaves;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.BlockLaLuzLog;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.Plants.BlockLaLuzSapling;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees.GenMarshSmokyTree;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees.GenNormalSmokyTree;
import teamHTBP.LaLuzdelAlba.Blocks.Environment.Trees.GenSmokyTree;
import teamHTBP.LaLuzdelAlba.LaLuzdelAlba;

/**用于注册方块的class,主要使用DefferedRegister进行注册*/
public class BlocksLoader {
    public BlocksLoader(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /**方块的DeferredRegister*/
    public final static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,LaLuzdelAlba.MODID);

    //从这里开始注册原木
    public final static RegistryObject<Block> SMOKY_LOG = BLOCKS.register("smoky_log", BlockLaLuzLog::new);
    public final static RegistryObject<Block> FUNGUS_SMOKY_LOG = BLOCKS.register("fungus_smoky_log", BlockLaLuzLog::new);
    public final static RegistryObject<Block> MARSH_SMOKY_LOG = BLOCKS.register("marsh_smoky_log", BlockLaLuzLog::new);
    public final static RegistryObject<Block> SPIRAL_SMOKY_LOG = BLOCKS.register("spiral_smoky_log", BlockLaLuzLog::new);


    //从这里开始注册树叶
    public final static RegistryObject<Block> SMOKY_LEAVES = BLOCKS.register("smoky_leaves", BlockLaLuzLeaves::new);
    public final static RegistryObject<Block> SPIRAL_LEAVES = BLOCKS.register("spiral_smoky_leaves", BlockLaLuzLeaves::new);


    //从这里开始注册泥土
    public final static RegistryObject<Block> SMOKY_DIRT = BLOCKS.register("smoky_dirt", BlockLaLuzDirt::new);

    //从这里开始注册树苗
    public final static RegistryObject<Block> SMOKY_SAPLING = BLOCKS.register("smoky_sapling",()->new BlockLaLuzSapling(GenSmokyTree::new));
    public final static RegistryObject<Block> MARSH_SMOKY_SAPLING = BLOCKS.register("marsh_smoky_sapling",()->new BlockLaLuzSapling(GenMarshSmokyTree::new));
    public final static RegistryObject<Block> NORMAL_SMOKY_SAPLING = BLOCKS.register("normal_smoky_sapling",()->new BlockLaLuzSapling(GenNormalSmokyTree::new));

}
