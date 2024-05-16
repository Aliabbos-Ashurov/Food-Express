package com.pdp.utils.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pdp.telegram.state.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JsonSerializer is a utility class for serializing Java objects to JSON format and deserializing JSON data back to Java objects using Gson library.
 * This class provides methods to write JSON data to a file, read JSON data from a file, and convert Java objects to JSON strings.
 *
 * @param <T> the type of objects to be serialized and deserialized
 * @author Aliabbos Ashurov
 * @since 29th April 2024, 14:03
 */
public class JsonSerializer<T> {
    private final Path path;
    private final Gson gson;

    /**
     * Constructs a new instance of JsonSerializer with the specified file path.
     *
     * @param path the path to the JSON file used for reading and writing JSON data
     */
    public JsonSerializer(Path path) {
        this.path = path;
        this.gson = createGson();
    }

    /**
     * Serializes a list of objects to JSON format and writes it to the JSON file specified during construction.
     *
     * @param data the list of objects to be serialized and written to JSON
     */
    public synchronized void write(List<?> data) throws IOException {
        String json = gson.toJson(data);
        Files.writeString(path, json);
    }

    /**
     * Reads JSON data from the JSON file specified during construction and deserializes it into a list of objects of type T.
     *
     * @param clazz the class of objects to deserialize into
     * @return a list of deserialized objects
     */
    public synchronized List<T> read(Class<T> clazz) throws IOException {
        String json = Files.readString(path);
        return gson.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

    /**
     * Serializes a single object to JSON format.
     *
     * @param object the object to be serialized to JSON
     * @return the JSON representation of the object
     */
    public String toJsonFormat(T object) {
        return gson.toJson(object);
    }

    private Gson createGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(State.class, new StateSerializer())
                .registerTypeAdapter(State.class, new StateDeserializer())
                .create();
    }
}

