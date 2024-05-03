package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.enums.OrderStatus;
import com.pdp.backend.web.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 03/May/2024  13:21
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class CustomerOrder {
    @SerializedName("user_id")
    private UUID userID;
    @SerializedName("branch_id")
    private UUID branchID;
    @SerializedName("address_id")
    private UUID addressID;
    @SerializedName("order_status")
    private OrderStatus orderStatus;
    @SerializedName("payment_type")
    private PaymentType paymentType;
    @SerializedName("deliver_id")
    private UUID deliverID;
}
