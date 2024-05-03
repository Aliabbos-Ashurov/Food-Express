package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:10
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Banned extends BaseModel {
    @SerializedName("user_id")
    private UUID userID;
    @SerializedName("banned_at")
    private LocalDateTime bannedAt;
    @SerializedName("description_id")
    private UUID descriptionID;
}
