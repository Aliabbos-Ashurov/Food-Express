package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:07
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Discount {
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
