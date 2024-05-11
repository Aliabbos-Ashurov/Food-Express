package com.pdp.web.model.order;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents an order for a specific food item, detailing the item, its price, quantity,
 * and a unique identifier for the order itself.
 * <p>
 * Inherits from {@link BaseModel}, which includes foundational properties such as a
 * unique identifier, a timestamp of creation, and a soft deletion status flag.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:17
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseModel {
    @SerializedName("food_id")
    private UUID foodID;
    @SerializedName("food_price")
    private BigDecimal foodPrice;
    @SerializedName("food_quantity")
    private int foodQuantity;
    @SerializedName("customer_order_id")
    private UUID customerOrderID;
}
