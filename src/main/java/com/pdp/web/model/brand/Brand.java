package com.pdp.web.model.brand;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.*;

import java.time.LocalDateTime;
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
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseModel implements Displayable {
    private String name;
    private double rating;
    @SerializedName("description_id")
    private UUID descriptionID;
    @SerializedName("image_url")
    private String imageURL;
    @SerializedName("opening_time")
    private LocalDateTime openingTime;
    @SerializedName("closing_time")
    private LocalDateTime closingTime;

    public Brand(String name, UUID descriptionID, String imageURL, LocalDateTime openingTime, LocalDateTime closingTime) {
        this.name = name;
        this.rating = 1.0;
        this.descriptionID = descriptionID;
        this.imageURL = imageURL;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    @Override
    public String getDisplayName() {
        return this.name;
    }
}
