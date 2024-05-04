package com.pdp.backend.web.model.description;

import com.pdp.backend.web.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a descriptive entity that provides detailed information about
 * items or entities within the system through its name and text content.
 *
 * <p>Inherits standard entity properties from {@link BaseModel}, including
 * a unique identifier, timestamp of creation, and soft deletion flag.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 12:56
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Description extends BaseModel {
    private String name;
    private String text;
}
