package com.pdp.backend.web.model.food;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
public class Food extends BaseModel {
    private String name;
    @SerializedName("pictur_id")
    private UUID pictureID;
    @SerializedName("description_id")
    private UUID descriptionID;
    private String price;
    @SerializedName("category_id")
    private UUID categoryID;
}
