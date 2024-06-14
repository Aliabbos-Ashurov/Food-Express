package com.pdp.web.model.banned;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a banned user entity in the system. It stores information about
 * the user who is banned, the time they were banned, and the description of the ban.
 *
 * <p>This class extends the {@link BaseModel} which contains core entity fields such as
 * unique identifier, creation timestamp, and soft deletion status.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:10
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Banned extends BaseModel {
    @SerializedName("user_id")
    private UUID userID;
    @SerializedName("banned_at")
    private LocalDateTime bannedAt;
    @SerializedName("description_id")
    private UUID descriptionID;
}
