package com.pdp.telegram.repository.telegramDeliverer;

import com.pdp.config.SQLConfiguration;
import com.pdp.enums.telegram.DeliveryStatus;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository class for managing Telegram deliverers.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:39
 */
public class TelegramDelivererRepository implements BaseRepository<TelegramDeliverer, List<TelegramDeliverer>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a Telegram deliverer to the repository.
     *
     * @param object The Telegram deliverer to add.
     * @return True if the addition was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NonNull TelegramDeliverer object) {
        return sql.executeUpdate("INSERT INTO telegram.telegramDeliverer(id,telegram_user_id,full_name,deliveryStatus) VALUES (?,?,?,?);",
                object.getId(), object.getTelegramUserID(), object.getFullname(), String.valueOf(object.getDeliveryStatus())) > 0;
    }

    /**
     * Removes a Telegram deliverer from the repository.
     *
     * @param id The ID of the Telegram deliverer to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM telegram.telegramDeliverer WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull TelegramDeliverer telegramDeliverer) {
        return sql.executeUpdate("UPDATE telegram.telegramdeliverer set telegram_user_id=?,full_name=?,deliverystatus=? WHERE id = ?;",
                telegramDeliverer.getTelegramUserID(), telegramDeliverer.getFullname(), String.valueOf(telegramDeliverer.getDeliveryStatus()), telegramDeliverer.getId()) > 0;
    }

    /**
     * Finds a Telegram deliverer by its ID.
     *
     * @param id The ID of the Telegram deliverer to find.
     * @return The Telegram deliverer with the specified ID, or null if not found.
     */
    @Override
    public TelegramDeliverer findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(telegramDeliverer -> telegramDeliverer.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all Telegram deliverers from the repository.
     *
     * @return A list of all Telegram deliverers.
     */
    @SneakyThrows
    @Override
    public List<TelegramDeliverer> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM telegram.telegramDeliverer;");
        List<TelegramDeliverer> telegramDeliverers = new ArrayList<>();
        while (resultSet.next()) {
            TelegramDeliverer telegramDeliverer = new TelegramDeliverer();
            telegramDeliverer.setId(resultSet.getObject("id", UUID.class));
            telegramDeliverer.setTelegramUserID(resultSet.getObject("telegram_user_id", UUID.class));
            telegramDeliverer.setFullname(resultSet.getString("full_name"));
            telegramDeliverer.setDeliveryStatus(DeliveryStatus.valueOf(resultSet.getString("deliveryStatus")));
            telegramDeliverer.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            telegramDeliverers.add(telegramDeliverer);
        }
        return telegramDeliverers;
    }
}
