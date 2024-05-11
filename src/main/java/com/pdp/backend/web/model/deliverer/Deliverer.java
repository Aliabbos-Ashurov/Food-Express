package com.pdp.backend.web.model.deliverer;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
@ToString(callSuper = true)
@AllArgsConstructor
public class Deliverer extends BaseModel implements Displayable {
    private String fullname;
    @SerializedName("phone_number")
    private String phoneNumber;

    @Override
    public String getDisplayName() {
        return this.fullname;
    }
}
