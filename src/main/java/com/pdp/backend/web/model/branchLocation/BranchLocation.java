package com.pdp.backend.web.model.branchLocation;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Represents a branch location with longitude and latitude coordinates.
 * Extends {@link BaseModel} to inherit common properties.
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:00
 */
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class BranchLocation extends BaseModel {
    @SerializedName("branch_id")
    private UUID branchID;
    private float latidue;
    private float longtidue;
}