package teamHTBP.LaLuzdelAlba.JsonGenerator.Properties;

import com.google.gson.Gson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockStateJsonWriter;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.BlockStateJson;

import java.io.FileWriter;
import java.util.Map;

/**主要负责BlockState的生成和写入*/
public class BlockStateProperties {

    private String blockStateName = "";
    private static final String STATE_PATH  = "src\\main\\resources\\assets\\laluzdelalba\\blockstates";

    IBlockStateJsonWriter jsonWriteState;
    public BlockStateProperties(String blockStateName,IBlockStateJsonWriter jsonWriteState) {
        this.blockStateName = blockStateName;
        this.jsonWriteState = jsonWriteState;
    }

    /***
     * 普通根据传入的stateWriter和modelWriter
     * 生成blockState文件和blockModel
     * @param gson gson实例
     */
    public void writeBlockState(Gson gson){
        try {
            Map<?, ?> varients = jsonWriteState.getVariants(blockStateName); //先通过jsonWriteState获取到Varients
            FileWriter writer = new FileWriter(STATE_PATH + "\\" + blockStateName + ".json");
            BlockStateJson blockStateJson = new BlockStateJson(varients);
            gson.toJson(blockStateJson, BlockStateJson.class, writer);
            writer.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
