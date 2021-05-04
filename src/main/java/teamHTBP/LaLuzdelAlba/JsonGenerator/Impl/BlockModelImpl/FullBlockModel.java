package teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl;

import com.google.common.collect.ImmutableMap;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.EnumBlockModelBasic;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockModelJsonWriter;

import java.util.Map;

import static teamHTBP.LaLuzdelAlba.JsonGenerator.Helper.BlockModelWriterHelper.MOD_ID;

public class FullBlockModel implements IBlockModelJsonWriter {
    private String textureName = "";

    public FullBlockModel(String textureName) {
        this.textureName = textureName;
    }

    public FullBlockModel() {
    }

    @Override
    public String getParent() {
        return EnumBlockModelBasic.Full.parent;
    }

    @Override
    public Map<String,String> getTextures(String name){
        if(!textureName.equals("")) return ImmutableMap.of("all",MOD_ID + ":block/" + textureName);
        return ImmutableMap.of("all",MOD_ID + ":block/" + name);
    }

}
