package teamHTBP.LaLuzdelAlba;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teamHTBP.LaLuzdelAlba.Blocks.BlocksLoader;
import teamHTBP.LaLuzdelAlba.Entities.EntitiesLoader;
import teamHTBP.LaLuzdelAlba.Items.ItemBlocksLoader;
import teamHTBP.LaLuzdelAlba.Items.ItemsLoader;
import teamHTBP.LaLuzdelAlba.WorldGeneration.FeaturesLoader;

@Mod("laluzdelalba")
public class LaLuzdelAlba {
    public final static Logger LOGGER = LogManager.getLogger();
    public final static String MODID = "laluzdelalba";

    public LaLuzdelAlba(){
        new BlocksLoader();
        new ItemBlocksLoader();
        new ItemsLoader();
        new EntitiesLoader();
        new FeaturesLoader();
    }
}
