package com.pdp.web.model.address;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import lombok.*;

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
@NoArgsConstructor
@Setter
public class Address extends BaseModel {
    private String city;
    private String street;
    @SerializedName("apartment_number")
    private int apartmentNumber;
    @SerializedName("house_number")
    private int houseNumber;
}
