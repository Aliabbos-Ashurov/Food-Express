package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:13
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Deliverer extends BaseModel {
    private String fullname;
    @SerializedName("phone_number")
    private String phoneNumber;
}
