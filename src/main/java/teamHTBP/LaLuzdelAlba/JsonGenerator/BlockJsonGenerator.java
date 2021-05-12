package teamHTBP.LaLuzdelAlba.JsonGenerator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.CrossBlockModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.FullBlockModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.BlockModelProperties;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockStateImpl.BasicStateWriterGenerator;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.Attrs.ItemModelJson;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.BlockStateProperties;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.ItemProperties;

import java.util.List;

public class BlockJsonGenerator {
    private List<BlockModelProperties> blocks;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static final BlockJsonGenerator GENERATOR = new BlockJsonGenerator();

    public List<BlockStateProperties> blockStateProperties;
    public List<BlockModelProperties> blockModelProperties;
    public List<ItemProperties> itemProperties;

    public static void main(String[] args){
        BlockJsonGenerator generator = new BlockJsonGenerator();
        generator.initializeState();
        generator.initializeModel();
        generator.initializeItem();
        generator.write();
    }

    public void initializeState(){
        blockStateProperties = ImmutableList.of(
                new BlockStateProperties("marsh_smoky_planks", BasicStateWriterGenerator.generateNoneAxis())
                );
    }

    public void initializeModel(){
        blockModelProperties = ImmutableList.of(
                new BlockModelProperties("marsh_smoky_planks",new FullBlockModel("plank_marsh_smoky_0","planks"))
        );
    }

    public void initializeItem(){
        itemProperties = ImmutableList.of(
                new ItemProperties("marsh_smoky_planks", "marsh_smoky_planks")
        );
    }

    public void write(){
        blockStateProperties.forEach(x -> x.writeBlockState(gson));
        blockModelProperties.forEach(x -> x.writeBlockBasicModel(gson));
        itemProperties.forEach(x -> x.writeBlockState(gson));
    }

}
