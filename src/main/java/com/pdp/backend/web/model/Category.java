package com.pdp.backend.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Aliabbos Ashurov
 * @since: 03/May/2024  13:00
 **/
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class Category extends BaseModel{
    private String name;
}
