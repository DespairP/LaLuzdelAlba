package teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.BlockModelImpl;

import com.google.common.collect.ImmutableMap;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.EnumBlockModelBasic;
import teamHTBP.LaLuzdelAlba.JsonGenerator.Impl.IBlockModelJsonWriter;

import java.util.Map;

import static teamHTBP.LaLuzdelAlba.JsonGenerator.Helper.BlockStateWriterHelper.MOD_ID;

public class CrossBlockModel implements IBlockModelJsonWriter {

    @Override
    public String getParent() {
        return EnumBlockModelBasic.Cross.parent;
    }

    @Override
    public Map<String, String> getTextures(String name) {
        return ImmutableMap.of("cross",MOD_ID + ":block/" + name);
    }
}
