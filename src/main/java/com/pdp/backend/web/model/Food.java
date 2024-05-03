package com.pdp.backend.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  12:28
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Food extends BaseModel{
    private String name;
    @SerializedName("pictur_id")
    private UUID pictureID;
    @SerializedName("description_id")
    private UUID descriptionID;
    private String price;
    @SerializedName("category_id")
    private UUID categoryID;
}
