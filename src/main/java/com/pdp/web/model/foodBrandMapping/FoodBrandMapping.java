package com.pdp.web.model.foodBrandMapping;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.util.UUID;

/**
 * Maps food items to their corresponding brands, establishing a many-to-many
 * relationship between food entities and brand entities within the system.
 * <p>
 * This class extends {@link BaseModel}, utilizing its foundational properties such
 * as a unique identifier, creation timestamp, and deletion status flag.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 12:44
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class FoodBrandMapping extends BaseModel {
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("food_id")
    private UUID foodID;
    @SerializedName("brand_id")
    private UUID brandID;
}
