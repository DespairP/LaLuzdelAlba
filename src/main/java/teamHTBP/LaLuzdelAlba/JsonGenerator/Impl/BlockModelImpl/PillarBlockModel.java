package teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl;

import com.google.common.collect.ImmutableMap;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.EnumBlockModelBasic;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockModelJsonWriter;

import java.util.Map;

import static teamHTBP.LaLuzdelAlba.JsonGenerator.Helper.BlockModelWriterHelper.MOD_ID;

public class PillarBlockModel implements IBlockModelJsonWriter {
    private String topTexture = "";
    private String sideTexture = "";
    private String subPath = "";

    public PillarBlockModel(String topTexture, String sideTexture) {
        this.topTexture = topTexture;
        this.sideTexture = sideTexture;
    }

    public PillarBlockModel(String topTexture, String sideTexture,String subPath) {
        this.topTexture = topTexture;
        this.sideTexture = sideTexture;
        this.subPath = subPath + "/";
    }

    public PillarBlockModel(){

    }

    @Override
    public String getParent() {
        return EnumBlockModelBasic.Pillar.parent;
    }

    @Override
    public Map<String, String> getTextures(String name) {
        if(topTexture.equals("") && sideTexture.equals(""))
            return ImmutableMap.of("end", MOD_ID + ":block/" + subPath + name + "_top","side",MOD_ID + ":block/" + subPath + name );
        return ImmutableMap.of("end",MOD_ID + ":block/" + subPath + topTexture + "_top","side",MOD_ID + ":block/" + subPath + sideTexture);
    }
}
