package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:19
 **/
@Getter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
public class Address extends BaseModel {

    private String city;
    private String street;
    @SerializedName("apartment_number")
    private int apartmentNumber;
    @SerializedName("house_number")
    private int houseNumber;
}
