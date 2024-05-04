package com.pdp.backend.web.model.picture;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.enums.format.PictureFormat;
import com.pdp.backend.web.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Encapsulates details about a picture associated with items in the system, such as its name,
 * format, dimensions, and a URL to the actual image.
 * <p>
 * Inherits from {@link BaseModel}, which includes foundational properties such as a
 * unique identifier, a timestamp of creation, and a status of presence in the system.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:01
 */
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
