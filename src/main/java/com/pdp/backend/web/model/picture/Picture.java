package com.pdp.backend.web.model.picture;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.enums.format.PictureFormat;
import com.pdp.backend.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
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
public class Picture extends BaseModel implements Displayable{
    private String name;
    private PictureFormat format;
    private boolean width;
    private boolean height;
    @SerializedName("image_url")
    private String imageUrl;

    public Picture(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.format = PictureFormat.JPEG;
    }

    @Override
    public String getDisplayName() {
        return this.name;
    }
}
