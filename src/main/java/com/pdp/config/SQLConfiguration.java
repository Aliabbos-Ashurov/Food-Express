package com.pdp.config;

import lombok.Getter;
import sql.helper.SQLHelper;

/**
 * @author Aliabbos Ashurov
 * @since 13/June/2024  14:52
 **/
public class SQLConfiguration {
    @Getter
    private static final SQLHelper SQL = new SQLHelper("jdbc:postgresql://localhost:5432/food_express", "postgres", "");
}
