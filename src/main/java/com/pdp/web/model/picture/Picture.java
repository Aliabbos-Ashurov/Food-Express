package com.pdp.web.model.picture;

import com.google.gson.annotations.SerializedName;
import com.pdp.enums.format.PictureFormat;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Picture extends BaseModel implements Displayable {
    private String name;
    private PictureFormat format;
    private int width;
    private int height;
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
