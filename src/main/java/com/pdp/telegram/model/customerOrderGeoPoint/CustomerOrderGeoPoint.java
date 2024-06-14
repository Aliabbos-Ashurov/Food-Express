package com.pdp.telegram.model.customerOrderGeoPoint;

import com.pdp.web.model.BaseModel;
import lombok.*;

/**
 * Represents the location of a Telegram user, identified by a Telegram user ID along with latitude and longitude coordinates.
 * This class is designed to store location information for a user within the Telegram messaging platform.
 *
 * @author Aliabbos Ashurov
 * @see BaseModel
 * @since 14/May/2024 11:20
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderGeoPoint extends BaseModel {
    private float lattidue;
    private float longtidue;
}
