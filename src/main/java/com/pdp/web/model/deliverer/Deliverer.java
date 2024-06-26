package com.pdp.web.model.deliverer;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.*;

/**
 * Defines the characteristics of a deliverer within the system,
 * including their full name and phone number.
 * <p>
 * Inherits from {@link BaseModel}, which includes fields for the
 * unique identifier, timestamp of creation, and a flag indicating
 * whether the entity has been soft-deleted.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:13
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Deliverer extends BaseModel implements Displayable {
    private String fullname;
    @SerializedName("phone_number")
    private String phoneNumber;

    @Override
    public String getDisplayName() {
        return this.fullname;
    }
}
