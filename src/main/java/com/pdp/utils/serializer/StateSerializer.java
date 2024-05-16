package com.pdp.utils.serializer;


import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pdp.telegram.state.State;

import java.lang.reflect.Type;

/**
 * The {@code StateSerializer} class is a custom serializer for handling {@code State} objects with Gson.
 * This class implements the {@link com.google.gson.JsonSerializer} interface for the {@code State} type,
 * allowing for custom serialization logic.
 *
 * @version 1.0
 * @autor Aliabbos Ashurov
 * @see com.google.gson.JsonSerializer
 * @see com.google.gson.GsonBuilder#registerTypeAdapter
 * @since 16/May/2024 09:46
 */
public class StateSerializer implements JsonSerializer<State> {
    @Override
    public JsonElement serialize(State state, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(state.getClass().getName() + "." + state.toString());
    }
}
