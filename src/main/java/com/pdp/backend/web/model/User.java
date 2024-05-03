package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import com.pdp.backend.web.enums.Language;
import com.pdp.backend.web.enums.Role;
import lombok.*;

/**
 * Represents a user entity within the system.
 *
 * <p>
 * The User class encapsulates user-related information such as full name, username,
 * password, contact details, authentication status, role, preferred language, and
 * registration information.
 * </p>
 *
 * <p>
 * This class is used to model user data and supports serialization/deserialization
 * using Gson annotations. Extends {@link BaseModel} to include basic model properties
 * such as id, creation time, and soft deletion status.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 30/April/2024 19:41
 */
@Getter
@Builder
@ToString(callSuper = true, exclude = {"profilPictureUrl", "isEmailVerified", "isNumberVerified"})
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
    private String fullname;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    @SerializedName("is_number_verified")
    private boolean isNumberVerified;
    @SerializedName("is_email_verified")
    private boolean isEmailVerified;
    private Role role;
    private Language language;
    @SerializedName("profil_picture_url")
    private String profilPictureUrl;
}
