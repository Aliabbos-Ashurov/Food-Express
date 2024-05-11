package com.pdp.web.model.discount;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Describes a discount applicable to a food item, detailing the timespan of the discount
 * and associated descriptive information.
 *
 * <p>Note: This class does not extend {@link BaseModel} and, as such, does not inherit common
 * entity properties such as an identifier or creation timestamp. Extend {@code BaseModel} if those properties are required.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:07
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Discount extends BaseModel {
    @SerializedName("food_id")
    private UUID foodID;
    @SerializedName("starting_time")
    private final LocalDateTime startingTime;
    @SerializedName("ending_time")
    private final LocalDateTime endingTime;
    @SerializedName("description_id")
    private UUID descriptionID;
    @SerializedName("discount_persentage")
    private boolean discountPersentage;
}
