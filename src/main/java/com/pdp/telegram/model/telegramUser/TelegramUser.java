package com.pdp.telegram.model.telegramUser;

import com.google.gson.annotations.SerializedName;
import com.pdp.java.console.support.Displayable;
import com.pdp.telegram.state.State;
import com.pdp.web.enums.Language;
import com.pdp.web.enums.role.Role;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.util.UUID;

/**
 * Represents a user within the Telegram messaging platform in a delivery service application.
 * This class extends the {@link BaseModel} and implements the {@link Displayable} interface.
 * It encapsulates information about a Telegram user, including their chat ID, first name,
 * username, phone number, role, state, language, and methods to interact with display-related functionalities.
 *
 * @author Aliabbos Ashurov
 * @since 14/May/2024 11:17
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramUser extends BaseModel implements Displayable {
    private Long chatID;
    private String firstName;
    private String username;
    @SerializedName("phone_number")
    private String phoneNumber;
    @Builder.Default
    private Role role = Role.USER;
    private State state;
    @Builder.Default
    private Language language = Language.EN;

    /**
     * Retrieves the display name of the Telegram user.
     *
     * @return The first name of the Telegram user.
     */
    @Override
    public String getDisplayName() {
        return this.firstName;
    }
}
