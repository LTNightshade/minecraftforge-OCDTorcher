package de.madone.ocdtorcher.world.feature;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class ResourceLocationJsonSerializer implements JsonSerializer<ResourceLocation>, JsonDeserializer<ResourceLocation> {

    @Override
    public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jo = json.getAsJsonObject();
            if (jo.has("namespace") & jo.has("path")) {
                return new ResourceLocation(jo.get("namespace").getAsString(), jo.get("path").getAsString());
            }
        }
        return new ResourceLocation(json.getAsString());
    }

    @Override
    public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
