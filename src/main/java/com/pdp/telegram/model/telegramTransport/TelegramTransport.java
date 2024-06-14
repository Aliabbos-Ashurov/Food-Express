package com.pdp.telegram.model.telegramTransport;

import com.google.gson.annotations.SerializedName;
import com.pdp.java.console.support.Displayable;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.util.UUID;

/**
 * Represents a transport associated with a Telegram deliverer in a delivery service application.
 * This class extends the {@link BaseModel} and implements the {@link Displayable} interface.
 * It provides information about a Telegram transport, including its unique identifier,
 * name, registered number, and methods to interact with display-related functionalities.
 *
 * @author Aliabbos Ashurov
 * @since 14/May/2024 11:43
 */
@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@NoArgsConstructor()
public class TelegramTransport extends BaseModel implements Displayable {
    @SerializedName("telegram_deliverer_id")
    private UUID telegramDelivererID;
    private String name;
    @SerializedName("registered_number")
    private String registeredNumber;

    /**
     * Retrieves the display name of the Telegram transport.
     *
     * @return The name of the Telegram transport.
     */
    @Override
    public String getDisplayName() {
        return this.name;
    }
}
