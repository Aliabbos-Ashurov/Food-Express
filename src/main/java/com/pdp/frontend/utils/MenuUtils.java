package com.pdp.frontend.utils;

import static com.pdp.java.console.Scan.scanInt;

/**
 * @author Doniyor Nishonov
 * Date: 10/May/2024  21:24
 **/
public interface MenuUtils {
    String MENU = """
            ----Menu----
            [1] Sign In
            [2] Sign Up
            [0] Exit
            """;
    static int menu(String str){
        return scanInt(str);
    }
}
