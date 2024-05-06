package com.pdp.backend.web.model.brand;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Models a brand entity with attributes capturing brand details.
 *
 * <p>Includes the brand's name, rating, a unique identifier for brand
 * description, and its operational hours represented by opening and closing times.</p>
 *
 * <p>Extends {@link BaseModel} to utilize foundational entity properties.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 12:26
 */
@Getter
@ToString(callSuper = true)
public class Brand extends BaseModel {
    private final String name;
    private final double rating;
    @SerializedName("description_id")
    private UUID descriptionID;
    @SerializedName("opening_time")
    private LocalDateTime openingTime;
    @SerializedName("closing_time")
    private LocalDateTime closingTime;
    public Brand(String name, UUID descriptionID, LocalDateTime openingTime, LocalDateTime closingTime) {
        this.name = name;
        this.rating = 1.0;
        this.descriptionID = descriptionID;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
