package teamHTBP.LaLuzdelAlba.JsonGenerator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.CrossBlockModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.FullBlockModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.LogHorizonModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.BlockModelProperties;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl.PillarBlockModel;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockStateImpl.BasicStateWriterGenerator;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Properties.ItemModelJson;

import java.util.List;

public class BlockJsonGenerator {
    private List<BlockModelProperties> blocks;

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static void main(String[] args) throws Exception {
        BlockJsonGenerator generator = new BlockJsonGenerator();
        generator.initialize();
        generator.write();
    }

    public void initialize(){
        blocks = ImmutableList.of(
                new BlockModelProperties("smoky_sapling",BasicStateWriterGenerator.generateNoneAxis(),new CrossBlockModel("sapling_smoky","plants"),new ItemModelJson("minecraft:item/generated", ImmutableMap.of("layer0","vida:block/sapling_smoky"))),
                new BlockModelProperties("marsh_smoky_sapling",BasicStateWriterGenerator.generateNoneAxis(),new CrossBlockModel("sapling_marsh_smoky","plants"),new ItemModelJson("minecraft:item/generated", ImmutableMap.of("layer0","vida:block/marsh_sapling_smoky")))

        );

    }

    public void write(){
        blocks.forEach(blockModelProperties -> {
            try {
                blockModelProperties.writeBlockState(gson);
                blockModelProperties.writeBlockBasicModel(gson);
                blockModelProperties.writeItem(gson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
