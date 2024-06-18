package com.pdp.telegram.repository.telegramUser;

import com.pdp.config.SQLConfiguration;
import com.pdp.enums.Language;
import com.pdp.enums.role.Role;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.*;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Repository class for managing Telegram users.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:43
 */
public class TelegramUserRepository implements BaseRepository<TelegramUser, List<TelegramUser>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a Telegram user to the repository.
     *
     * @param object The Telegram user to add.
     * @return True if the addition was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NonNull TelegramUser object) {
        return Objects.nonNull(sql.executeQuery("select telegram.add_t_user(?,?,?,?,?,?,?);",
                object.getChatID(), object.getFirstName(), object.getUsername(), object.getPhoneNumber(),
                String.valueOf(object.getState()), String.valueOf(object.getRole()), String.valueOf(object.getLanguage())));
    }

    /**
     * Removes a Telegram user from the repository.
     *
     * @param id The ID of the Telegram user to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM telegram.telegramUser WHERE id = ?", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull TelegramUser telegramUser) {
        return sql.executeUpdate("UPDATE telegram.telegramuser set chat_id=?,first_name=?,user_name=?,phone_number=?,role=?,state=?,language=? WHERE id = ?;",
                telegramUser.getChatID(), telegramUser.getFirstName(), telegramUser.getUsername(), telegramUser.getPhoneNumber(), String.valueOf(telegramUser.getRole())
                , String.valueOf(telegramUser.getState()), String.valueOf(telegramUser.getLanguage()), telegramUser.getId()) > 0;
    }

    /**
     * Finds a Telegram user by its ID.
     *
     * @param id The ID of the Telegram user to find.
     * @return The Telegram user with the specified ID, or null if not found.
     */
    @Override
    public TelegramUser findById(@NonNull UUID id) {
        return getAll().stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all Telegram users from the repository.
     *
     * @return A list of all Telegram users.
     */
    @SneakyThrows
    @Override
    public List<TelegramUser> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM telegram.telegramuser;");
        List<TelegramUser> users = new ArrayList<>();
        while (resultSet.next()) {
            TelegramUser telegramUser = new TelegramUser();
            telegramUser.setId(UUID.fromString(resultSet.getString("id")));
            telegramUser.setChatID(resultSet.getLong("chat_id"));
            telegramUser.setFirstName(resultSet.getString("first_name"));
            telegramUser.setUsername(resultSet.getString("user_name"));
            telegramUser.setPhoneNumber(resultSet.getString("phone_number"));
            telegramUser.setRole(Role.valueOf(resultSet.getString("role")));
            telegramUser.setState(getUserState(resultSet.getString("state")));
            telegramUser.setLanguage(Language.valueOf(resultSet.getString("language")));
            telegramUser.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            users.add(telegramUser);
        }
        return users;
    }

    private State getUserState(String state) {
        try {
            return DefaultState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return UserViewState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return UserMenuOptionState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return OrderPlacementState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return OrderManagementState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return MyOrderState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return CourierRegistrationState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return ConfirmOrderState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return ConfirmationState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return ActiveOrderManagementState.valueOf(state);
        } catch (Exception ignored) {
        }
        try {
            return DeliveryMenuState.valueOf(state);
        } catch (Exception ignored) {
        }
        return null;
    }
}
