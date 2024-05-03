package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.enums.PictureFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:01
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Picture extends BaseModel {
    private String name;
    private PictureFormat format;
    private final boolean width;
    private final boolean height;
    @SerializedName("image_url")
    private String imageUrl;
}
