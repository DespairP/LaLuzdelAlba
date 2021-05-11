package teamHTBP.LaLuzdelAlba.JsonGenerator.Properties;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ItemModelJson {
    @SerializedName("parent")
    public String parent;
    @SerializedName("textures")
    public Map<String,String> textures;

    public ItemModelJson(String parent, Map<String,String> textures) {
        this.parent = parent;
        this.textures = textures;
    }
}
