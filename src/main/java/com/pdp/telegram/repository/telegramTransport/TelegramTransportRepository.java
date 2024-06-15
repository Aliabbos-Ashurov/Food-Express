package com.pdp.telegram.repository.telegramTransport;

import com.pdp.config.SQLConfiguration;
import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.web.repository.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Repository class for managing Telegram transports.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:42
 */
public class TelegramTransportRepository implements BaseRepository<TelegramTransport, List<TelegramTransport>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a Telegram transport to the repository.
     *
     * @param tt The Telegram transport to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull TelegramTransport tt) {
        return sql.executeUpdate("INSERT INTO telegram.telegram_transport(t_deliverer_id,name,registered_number) VALUES(?,?,?);",
                tt.getTelegramDelivererID(), tt.getName(), tt.getRegisteredNumber()) > 0;
    }

    /**
     * Removes a Telegram transport from the repository.
     *
     * @param id The ID of the Telegram transport to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM telegram.telegramtranpost WHERE id = ?;") > 0;
    }

    /**
     * Finds a Telegram transport by its ID.
     *
     * @param id The ID of the Telegram transport to find.
     * @return The Telegram transport with the specified ID, or null if not found.
     */
    @Override
    public TelegramTransport findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all Telegram transports from the repository.
     *
     * @return A list of all Telegram transports.
     */
    @Override
    @SneakyThrows
    public List<TelegramTransport> getAll() {
        List<TelegramTransport> telegramTransports = new ArrayList<>();
        ResultSet rs = sql.executeQuery("SELECT * FROM telegram.telegramtransport;");
        while (rs.next()) {
            TelegramTransport telegramTransport = new TelegramTransport();
            telegramTransport.setId(UUID.fromString(rs.getString(1)));
            telegramTransport.setTelegramDelivererID(UUID.fromString(rs.getString(2)));
            telegramTransport.setName(rs.getString(3));
            telegramTransport.setRegisteredNumber(rs.getString(4));
            telegramTransport.setCreatedAt(rs.getTimestamp(5).toLocalDateTime());
            telegramTransports.add(telegramTransport);
        }
        return telegramTransports;
    }
}
