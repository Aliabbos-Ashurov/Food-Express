package com.pdp.utils.front;

import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;

/**
 * @author Doniyor Nishonov
 * Date: 10/May/2024  21:24
 **/
public interface MenuUtils {
    //---USER MENU
    String BACK = "main.menu.back";
    String LANGUAGE = "main.menu.language";
    String SIGN_IN_UP = "main.menu.signInUP";
    String USER_MENU_AFTER_LOG = "menu.after.log.department";
    String MAKE_ORDER = "menu.after.department.order";
    String USER_ORDER = "menu.after.department.my.order";
    String CART_OPERATION = "menu.after.department.order.cart";
    String CONFIRMATION = "menu.confirmation";

    // DELIVERER
    String DELIVERER_MENU = "main.menu.deliver";
    String DELIVERER_GENERAL_MENU = "menu.deliver.order.general";
    String DELIVERER_GENERAL_FOLLOWING = "menu.deliver.order.general.following";

    static void menu(String menuKey, Language language) {
        String result = language.equals(Language.UZ)
                ? MessageSourceUtils.getLocalizedMessage(menuKey, Language.UZ)
                : MessageSourceUtils.getLocalizedMessage(menuKey, Language.EN);
        System.out.println(result);
    }
}
