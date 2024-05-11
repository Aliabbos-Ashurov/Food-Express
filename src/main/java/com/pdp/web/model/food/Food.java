package com.pdp.web.model.food;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a food item in the system, holding its name, associated image, description,
 * price, and category identification.
 * <p>
 * Inherits from {@link BaseModel}, which includes foundational properties like a unique
 * identifier and metadata like creation timestamp and deletion flag.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 12:28
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Food extends BaseModel implements Displayable {
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("description_id")
    private UUID descriptionID;
    private BigDecimal price;
    @SerializedName("category_id")
    private UUID categoryID;

    @Override
    public String getDisplayName() {
        return this.name;
    }
}
