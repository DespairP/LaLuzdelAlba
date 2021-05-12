package teamHTBP.LaLuzdelAlba.JsonGenerator.Properties;

import com.google.gson.Gson;
import net.minecraftforge.fml.common.Mod;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.BlockStateJson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.ItemModelJson;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static teamHTBP.LaLuzdelAlba.LaLuzdelAlba.MODID;

public class ItemProperties {
    private String itemName = "";
    public ItemModelJson itemModelJson;

    private static final String ITEM_PATH = "src\\main\\resources\\assets\\laluzdelalba\\models\\item";

    public ItemProperties(String itemName, ItemModelJson itemModelJson) {
        this.itemName = itemName;
        this.itemModelJson = itemModelJson;
    }

    public ItemProperties(String itemName, String parent) {
        this.itemName = itemName;
        this.itemModelJson = new ItemModelJson(MODID + ":block/" +parent, null);
    }

    public ItemProperties(String itemName,boolean isItem,String... layers) {
        this.itemName = itemName;
        Map<String,String> layerMap = new LinkedHashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        Arrays.stream(layers).forEach(
                layer-> layerMap.put("layer" + index.getAndIncrement(),MODID + ":" + (isItem?"Item/":"Block/") +layer)
        );
        this.itemModelJson = new ItemModelJson("minecraft:item/generated", layerMap);

    }

    public void writeBlockState(Gson gson) {
        try {
            FileWriter writer = new FileWriter(ITEM_PATH + "\\" + itemName + ".json");
            gson.toJson(itemModelJson, ItemModelJson.class, writer);
            writer.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
