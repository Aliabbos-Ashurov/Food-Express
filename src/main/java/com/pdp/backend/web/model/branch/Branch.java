package com.pdp.backend.web.model.branch;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
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
    @SerializedName("location_id")
    private final UUID locationID;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("phone_number")
    private String phoneNumber;

    public Branch(UUID brandID, UUID locationID, String phoneNumber) {
        this.brandID = brandID;
        this.locationID = locationID;
        this.isActive = true;
        this.phoneNumber = phoneNumber;
    }
}
