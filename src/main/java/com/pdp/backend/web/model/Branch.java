package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Represents a branch of a brand within the system. Contains details about the
 * brand's unique identifier, location of the branch, its active status, and contact number.
 *
 * <p>This class extends {@link BaseModel}, which includes fields for basic entity
 * characteristics like unique identifier, creation time, and deletion status.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 12:57
 */
@Getter
@ToString(callSuper = true)
public class Branch extends BaseModel{
    @SerializedName("brand_id")
    private UUID brandID;
    private final String location;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("phone_number")
    private String phoneNumber;

    public Branch(UUID brandID, String location, String phoneNumber) {
        this.brandID = brandID;
        this.location = location;
        this.isActive = true;
        this.phoneNumber = phoneNumber;
    }
}
