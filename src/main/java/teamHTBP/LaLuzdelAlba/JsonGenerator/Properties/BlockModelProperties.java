package teamHTBP.LaLuzdelAlba.JsonGenerator.Properties;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockModelJsonWriter;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockStateJsonWriter;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.BlockModelJson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.BlockStateJson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.ItemModelJson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static teamHTBP.LaLuzdelAlba.LaLuzdelAlba.MODID;

public class BlockModelProperties {
    private String blockName = "";
    Map<String,IBlockModelJsonWriter> modelJsonWriterMap;

    private static final String MODEL_PATH = "src\\main\\resources\\assets\\laluzdelalba\\models\\block";


    public BlockModelProperties(String blockName, Map<String,IBlockModelJsonWriter> modelJsonWriterMap) {
        this.blockName = blockName;
    }

    public BlockModelProperties(String blockName, IBlockModelJsonWriter modelJsonWriter) {
        this.blockName = blockName;
        this.modelJsonWriterMap = ImmutableMap.of("",modelJsonWriter);
    }

    public void writeBlockBasicModel(Gson gson){
        modelJsonWriterMap.forEach((subName,writter)->{
            try {
                FileWriter writer = null;
                if (!subName.equals("")) writer = new FileWriter(MODEL_PATH + "\\" + blockName + "_" + subName + ".json");
                else writer = new FileWriter(MODEL_PATH + "\\" + blockName + ".json");
                BlockModelJson blockModelJson = new BlockModelJson(writter.getParent(), writter.getTextures(blockName));
                gson.toJson(blockModelJson, BlockModelJson.class, writer);
                writer.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

}
