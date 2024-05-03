package com.pdp.backend.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a categorization for items or entities within the system. Each category
 * has a unique name that describes the type or classification it represents.
 *
 * <p>Inheritance from {@link BaseModel} allows the category to possess standard
 * entity properties such as an identifier and creation timestamp.</p>
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:00
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Category extends BaseModel {
    private String name;
}
