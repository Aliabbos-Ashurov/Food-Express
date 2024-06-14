package com.pdp.web.model.comment;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.*;

import java.util.UUID;

/**
 * Encapsulates a comment made on a particular food item, linking the
 * user-generated content to the associated food entity within the system.
 *
 * <p>This class extends {@link BaseModel} to benefit from baseline
 * entity properties such as identifier and creation timestamp.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:06
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Comment extends BaseModel implements Displayable {
    @SerializedName("food_id")
    private UUID foodID;
    private String text;

    @Override
    public String getDisplayName() {
        return this.text;
    }
}
