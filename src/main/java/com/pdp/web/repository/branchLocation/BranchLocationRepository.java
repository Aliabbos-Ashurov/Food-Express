package com.pdp.web.repository.branchLocation;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.branchLocation.BranchLocation;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing {@link BranchLocation} entities.
 * Implements {@link BaseRepository} to provide CRUD operations for branch locations.
 * This repository interacts with a {@link JsonSerializer} to serialize and
 * deserialize {@link BranchLocation} objects to and from a storage file specified by
 * {@link JsonFilePath#PATH_BRANCH_LOCATION}.
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:01
 */
public class BranchLocationRepository implements BaseRepository<BranchLocation, List<BranchLocation>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new {@link BranchLocation} to the repository. This involves
     * adding it to the in-memory list and persisting this list to the filesystem.
     * This method is thread-safe.
     *
     * @param branchLocation The new {@link BranchLocation} to add.
     * @return {@code true} if the branch location is added and persisted successfully.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull BranchLocation branchLocation) {
        return sql.executeUpdate("INSERT INTO web.branch_location(branch_id,lattidue,longtide) VALUES(?,?,?);",
                branchLocation.getBranchID(), branchLocation.getLatidue(), branchLocation.getLongtidue()) > 0;
    }

    /**
     * Removes a {@link BranchLocation} from the repository identified by the
     * provided UUID. This will update the in-memory list and persist the change.
     *
     * @param id The UUID of the {@link BranchLocation} to remove.
     * @return {@code true} if the branch location is found and removed.
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.branch_location WHERE id = ?;") > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull BranchLocation branchLocation) {
        return sql.executeUpdate("UPDATE web.branch_location set branch_id = ?, lattidue = ?, longtidue = ? WHERE id = ?;",
                branchLocation.getBranchID(), branchLocation.getLatidue(), branchLocation.getLongtidue(), branchLocation.getId()) > 0;
    }

    /**
     * Retrieves a {@link BranchLocation} by its UUID. If no matching
     * branch location is found, returns {@code null}.
     *
     * @param id The UUID of the {@link BranchLocation} to find.
     * @return The found {@link BranchLocation} or {@code null} if not found.
     */
    @Override
    public BranchLocation findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(branchLocation -> branchLocation.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtains all branch locations maintained by this repository.
     *
     * @return A list of {@link BranchLocation} objects.
     */
    @Override
    @SneakyThrows
    public List<BranchLocation> getAll() {
        ResultSet rs = sql.executeQuery("SELECT * FROM web.branch_location;");
        List<BranchLocation> branchLocations = new ArrayList<>();
        while (rs.next()) {
            BranchLocation bl = new BranchLocation();
            bl.setId(UUID.fromString(rs.getString(1)));
            bl.setBranchID(UUID.fromString(rs.getString(2)));
            bl.setLatidue(rs.getDouble(3));
            bl.setLongtidue(rs.getDouble(4));
            bl.setCreatedAt(rs.getTimestamp(5).toLocalDateTime());
            branchLocations.add(bl);
        }
        return branchLocations;
    }
}
