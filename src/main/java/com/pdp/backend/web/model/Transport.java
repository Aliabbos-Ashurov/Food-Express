package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:14
 **/
@Getter
@ToString(callSuper = true, exclude = {"pictureID"})
@AllArgsConstructor
public class Transport extends BaseModel {
    @SerializedName("deliver_id")
    private UUID deliverID;
    private String name;
    @SerializedName("registered_number")
    private String registeredNumber;
    @SerializedName("picture_id")
    private UUID pictureID;
}
