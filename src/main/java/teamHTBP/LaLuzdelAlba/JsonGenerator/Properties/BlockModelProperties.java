package teamHTBP.LaLuzdelAlba.JsonGenerator.Properties;

import com.google.gson.Gson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.LogHorizonModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockModelJsonWriter;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockStateJsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static teamHTBP.LaLuzdelAlba.LaLuzdelAlba.MODID;

public class BlockModelProperties {
    private String blockName = "";
    IBlockStateJsonWriter jsonWriteState;
    IBlockModelJsonWriter jsonWriteModel;
    IBlockModelJsonWriter subWriter;
    ItemModelJson itemModelJson;
    int generateModelJsonNumber = 1;
    public boolean generateSubModel = false;
    private static final String STATE_PATH  = "src\\main\\resources\\assets\\laluzdelalba\\blockstates";
    private static final String MODEL_PATH = "src\\main\\resources\\assets\\laluzdelalba\\models\\block";
    private static final String ITEM_PATH = "src\\main\\resources\\assets\\laluzdelalba\\models\\item";

    public BlockModelProperties(String blockName, IBlockStateJsonWriter jsonWriteState, IBlockModelJsonWriter jsonWriteModel) {
        this.blockName = blockName;
        this.jsonWriteState = jsonWriteState;
        this.jsonWriteModel = jsonWriteModel;
    }

    public BlockModelProperties(String blockName, IBlockStateJsonWriter jsonWriteState, IBlockModelJsonWriter jsonWriteModel,ItemModelJson itemModelJson) {
        this.blockName = blockName;
        this.jsonWriteState = jsonWriteState;
        this.jsonWriteModel = jsonWriteModel;
        this.itemModelJson = itemModelJson;
    }

    public BlockModelProperties(String blockName, IBlockStateJsonWriter jsonWriteState, IBlockModelJsonWriter jsonWriteModel,int generateModelJsonNumber) {
        this.blockName = blockName;
        this.jsonWriteState = jsonWriteState;
        this.jsonWriteModel = jsonWriteModel;
        this.generateModelJsonNumber = generateModelJsonNumber;
    }

    public BlockModelProperties(String blockName, IBlockStateJsonWriter jsonWriteState, IBlockModelJsonWriter jsonWriteModel,IBlockModelJsonWriter subWriter) {
        this.blockName = blockName;
        this.jsonWriteState = jsonWriteState;
        this.jsonWriteModel = jsonWriteModel;
        this.subWriter = subWriter;
    }

    /***
     * 普通根据传入的stateWriter和modelWriter
     * 生成blockState文件和blockModel
     * @param gson gson实例
     */
    public void writeBlockState(Gson gson) throws Exception {
        Map varients = jsonWriteState.getVariants(blockName);
        FileWriter writer = new FileWriter(STATE_PATH + "\\" + blockName + ".json");
        BlockStateJson blockStateJson = new BlockStateJson(varients);
        gson.toJson(blockStateJson,BlockStateJson.class,writer);
        writer.close();
    }


    public void writeBlockBasicModel(Gson gson,String subName) throws Exception{
        this.writeBlockBasicModel(gson); //写入普通的model
        BlockModelJson blockModelJson = new BlockModelJson(subWriter.getParent(), subWriter.getTextures(blockName));
        FileWriter writer = new FileWriter(MODEL_PATH + "\\" + blockName + "_" +subName + ".json");
        gson.toJson(blockModelJson,BlockModelJson.class,writer);
        writer.close();
    }

    public void writeBlockBasicModel(Gson gson) throws IOException {
        if(generateModelJsonNumber == 1){
                BlockModelJson blockModelJson = new BlockModelJson(jsonWriteModel.getParent(), jsonWriteModel.getTextures(blockName));
                FileWriter writer = new FileWriter(MODEL_PATH + "\\" + blockName + ".json");
                gson.toJson(blockModelJson,BlockModelJson.class,writer);
                writer.close();
                return;
        }
        for (int i = 0; i < generateModelJsonNumber; i++) {
            BlockModelJson blockModelJson = new BlockModelJson(jsonWriteModel.getParent(), jsonWriteModel.getTextures(blockName + i));
            FileWriter writer = new FileWriter(MODEL_PATH + "\\" + blockName + i + ".json");
            gson.toJson(blockModelJson,BlockModelJson.class,writer);
            writer.close();
        }
    }

    public void writeItem(Gson gson) throws IOException {
        if(itemModelJson == null) itemModelJson = new ItemModelJson(MODID +":block/" + blockName, null);
        FileWriter writer = new FileWriter(ITEM_PATH + "\\" + blockName + ".json");
        gson.toJson(itemModelJson,ItemModelJson.class,writer);
        writer.close();
    }

    public String getBlockName() {
        return blockName;
    }
}
