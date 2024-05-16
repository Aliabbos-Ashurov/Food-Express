package com.pdp.utils.serializer;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code LocalDateTimeAdapter} class is a custom serializer and deserializer for handling {@code LocalDateTime} objects with Gson.
 * It formats {@code LocalDateTime} objects to and from a specific string representation ("dd/MM/yyyy HH:mm:ss").
 * This class extends the {@link com.google.gson.TypeAdapter} for {@code LocalDateTime} and overrides the
 * {@code write} and {@code read} methods to provide custom serialization and deserialization logic.
 * <p>
 * Null values are supported and handled appropriately during serialization and deserialization.
 *
 * @version 1.0
 * @autor Aliabbos Ashurov
 * @see com.google.gson.TypeAdapter
 * @see com.google.gson.GsonBuilder#registerTypeAdapter
 * @since 29/April/2024 14:07
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
        } else {
            String formattedDateTime = localDateTime.format(FORMATTER);
            jsonWriter.value(formattedDateTime);
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            String dateTimeStr = jsonReader.nextString();
            return LocalDateTime.parse(dateTimeStr, FORMATTER);
        }
    }
}