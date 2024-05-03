package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents an address in the system, containing details about the city,
 * street, apartment number, and house number.
 *
 * <p>This class extends {@link BaseModel}, inheriting fields like unique identifier,
 * creation timestamp, and deletion status.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:19
 */
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
