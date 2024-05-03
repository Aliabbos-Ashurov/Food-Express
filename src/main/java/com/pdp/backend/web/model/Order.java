package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:17
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Order extends BaseModel {
    @SerializedName("food_id")
    private UUID foodID;
    @SerializedName("food_price")
    private BigDecimal foodPrice;

    @SerializedName("food_quantity")
    private int foodQuantity;
    @SerializedName("my_order_id")
    private UUID myOrderID;

}
