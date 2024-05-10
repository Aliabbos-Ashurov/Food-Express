package com.pdp.backend.web.model.comment;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
public class Comment extends BaseModel implements Displayable {
    @SerializedName("food_id")
    private UUID foodID;
    private String text;

    @Override
    public String getDisplayName() {
        return this.text;
    }
}
