package com.pdp.web.repository.branch;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implements a repository for managing Branch entities, providing CRUD operations,
 * such as addition, removal, searching by ID, and retrieval of all branches.
 * <p>
 * This class interacts with the JsonSerializer for serializing and deserializing
 * Branch objects to a JSON formatted storage, making the data persistent across
 * application sessions.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:48
 */
public class BranchRepository implements BaseRepository<Branch, List<Branch>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new Branch entity to the repository and serializes the updated list
     * to permanent storage through {@link JsonSerializer}.
     * <p>
     * This method will add the Branch to an in-memory list and save the entire list
     * to the path defined by {@code PATH_BRANCH}.
     *
     * @param branch The Branch entity to be added to the repository.
     * @return True if the Branch has been successfully added, false otherwise.
     * @throws Exception If an error occurs during the saving process.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull Branch branch) {
        return sql.executeUpdate("INSERT INTO web.branch(location_id,is_activate,phone_number) VALUES(?,?,?);",
                branch.getLocationID(), branch.isActive(), branch.getPhoneNumber()) > 0;
    }

    /**
     * Removes the Branch with the specified UUID identifier from the repository
     * and persists the updated list.
     *
     * @param id The UUID of the Branch object to remove.
     * @return True if successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.branch WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Branch branch) {
        return sql.executeUpdate("UPDATE web.branch set location_id = ?, is_active = ?, phone_number = ? WHERE id = ?;",
                branch.getLocationID(), branch.isActive(), branch.getPhoneNumber(), branch.getId()) > 0;
    }

    /**
     * Retrieves the Branch with the specified UUID identifier from the repository.
     *
     * @param id The UUID to search for.
     * @return The Branch object if found, null otherwise.
     */
    @Override
    public Branch findById(@NonNull UUID id) {
        return getAll().stream()
                .filter((branch -> branch.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }


    @Override
    @SneakyThrows
    public List<Branch> getAll() {
        ResultSet rs = sql.executeQuery("SELECT * FROM web.branch;");
        List<Branch> branches = new ArrayList<>();
        while (rs.next()) {
            Branch branch = new Branch();
            branch.setId(UUID.fromString(rs.getString(1)));
            branch.setLocationID(UUID.fromString(rs.getString(2)));
            branch.setActive(rs.getBoolean(3));
            branch.setPhoneNumber(rs.getString(4));
            branch.setCreatedAt(rs.getTimestamp(5).toLocalDateTime());
            branch.setBrandID(UUID.fromString(rs.getString(6)));
            branches.add(branch);
        }
        return branches;
    }
}
