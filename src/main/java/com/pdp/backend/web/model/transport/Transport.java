package com.pdp.backend.web.model.transport;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Represents a transportation entity for delivery purposes within the system.
 * It stores information about the delivery service or vehicle, including its name,
 * registered number, and an associated picture.
 * <p>
 * Inherits common properties from {@link BaseModel}.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:14
 */
@Getter
@ToString(callSuper = true, exclude = {"pictureID"})
@AllArgsConstructor
public class Transport extends BaseModel implements Displayable {
    @SerializedName("deliver_id")
    private UUID deliverID;
    private String name;
    @SerializedName("registered_number")
    private String registeredNumber;
    @SerializedName("picture_id")
    private UUID pictureID;

    @Override
    public String getDisplayName() {
        return this.name;
    }
}
