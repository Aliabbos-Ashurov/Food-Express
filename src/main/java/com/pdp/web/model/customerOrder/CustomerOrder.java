package com.pdp.web.model.customerOrder;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.enums.PaymentType;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents an order placed by a customer within the system, detailing association
 * with user, branch, and delivery addresses as well as the order status and payment type.
 *
 * <p>Note: This class does not extend {@link BaseModel}, and as such, it does not inherit
 * common entity framework properties like an identifier and creation timestamp. If this
 * behavior is not intentional, consider extending {@code BaseModel}.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:21
 */
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class CustomerOrder extends BaseModel{
    @SerializedName("user_id")
    private UUID userID;
    @SerializedName("branch_id")
    private UUID branchID;
    @SerializedName("address_id")
    private UUID addressID;
    @SerializedName("order_status")
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_CONFIRMED;
    @SerializedName("order_price")
    private BigDecimal orderPrice;
    @SerializedName("payment_type")
    private PaymentType paymentType;
    @SerializedName("deliver_id")
    private UUID deliverID;
}
