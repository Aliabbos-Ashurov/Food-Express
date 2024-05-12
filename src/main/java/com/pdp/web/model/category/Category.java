package com.pdp.web.model.category;

import com.google.gson.annotations.SerializedName;
import com.pdp.web.model.BaseModel;
import com.pdp.java.console.support.Displayable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

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
public class Category extends BaseModel implements Displayable {
    private String name;
    @SerializedName("brand_id")
    private UUID brandId;
    @SerializedName("image_url")
    private String imageUrl;

    @Override
    public String getDisplayName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(brandId, category.brandId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, brandId);
    }
}
