package com.pdp.telegram.model.telegramDeliverer;

import com.google.gson.annotations.SerializedName;
import com.pdp.java.console.support.Displayable;
import com.pdp.enums.telegram.DeliveryStatus;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.util.UUID;

/**
 * Represents a deliverer associated with a Telegram user in a delivery service application.
 * This class extends the {@link BaseModel} and implements the {@link Displayable} interface.
 * It provides information about a Telegram deliverer, including their Telegram user ID,
 * full name, and methods to interact with display-related functionalities.
 *
 * @author Aliabbos Ashurov
 * @since 14/May/2024 11:41
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TelegramDeliverer extends BaseModel implements Displayable {
    @SerializedName("telegram_user_id")
    private UUID telegramUserID;
    private String fullname;
    @Builder.Default
    private DeliveryStatus deliveryStatus = DeliveryStatus.EMPTY;

    /**
     * Retrieves the display name of the Telegram deliverer.
     *
     * @return The full name of the Telegram deliverer.
     */
    @Override
    public String getDisplayName() {
        return this.fullname;
    }
}
