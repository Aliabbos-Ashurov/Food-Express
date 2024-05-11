package com.pdp.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * The abstract base class for all model entities in the application.
 * Provides common properties such as id, creation time, and soft deletion status.
 * This class cannot be instantiated directly and must be extended by concrete entity classes.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024
 */
@Getter
@ToString
public abstract class BaseModel {

    /**
     * The unique identifier for the model instance. It is automatically generated upon instantiation.
     */
    private UUID id;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("is_deleted")
    private boolean isDeleted;

    public BaseModel() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Tashkent"));
        isDeleted = false;
    }
}
