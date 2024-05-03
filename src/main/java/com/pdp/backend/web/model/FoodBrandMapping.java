package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  12:44
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class FoodBrandMapping extends BaseModel {

    @SerializedName("food_id")
    private UUID foodID;
    @SerializedName("brand_id")
    private UUID brandID;
}
