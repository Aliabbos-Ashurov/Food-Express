package com.pdp.utils.factory;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.java.console.support.Displayable;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.BaseModel;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.category.CategoryService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:29
 **/
public class ReplyKeyboardMarkupFactory {
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final CategoryService categoryService = ThreadSafeBeansContainer.categoryServiceThreadLocal.get();

    public static ReplyKeyboardMarkup selectLangButtons() {
        return makeReplyKeyboardButtonsByStringsList(List.of("UZ \uD83C\uDDFA\uD83C\uDDFF", "EN \uD83C\uDDFA\uD83C\uDDF8"), false);
    }

    public static ReplyKeyboardMarkup confirmLocationButton() {
        return new ReplyKeyboardMarkup(new KeyboardButton("Location \uD83D\uDCCC").requestLocation(true))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true);
    }

    public static ReplyKeyboardMarkup confirmContactButton() {
        return new ReplyKeyboardMarkup(new KeyboardButton("Contact \uD83D\uDCDE").requestContact(true))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true);
    }

    public static ReplyKeyboardMarkup backButton(Language language) {
        String backMessage = getBackMessage(language);
        return new ReplyKeyboardMarkup(new KeyboardButton(backMessage))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true);
    }

    public static ReplyKeyboardMarkup registrationButtons(Language language) {
        String singIn = MessageSourceUtils.getLocalizedMessage("button.signIn", language);
        String singUp = MessageSourceUtils.getLocalizedMessage("button.signUp", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(singIn, singUp), false);
    }

    public static ReplyKeyboardMarkup userButtons(Language language) {
        String placeOrder = MessageSourceUtils.getLocalizedMessage("button.placeOrder", language);
        String myOrders = MessageSourceUtils.getLocalizedMessage("button.myOrders", language);
        String registerAsACourier = MessageSourceUtils.getLocalizedMessage("button.registerCourier", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(placeOrder, myOrders, registerAsACourier), true);
    }

    public static ReplyKeyboardMarkup orderProcessButtons1(Language language) {
        String gotFromPlace = MessageSourceUtils.getLocalizedMessage("order.got.place", language);
        String orderFailed = MessageSourceUtils.getLocalizedMessage("order.failed", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(gotFromPlace, orderFailed), true, getBackMessage(language));
    }

    public static ReplyKeyboardMarkup orderProcessButtons2(Language language) {
        String gotFromPlace = MessageSourceUtils.getLocalizedMessage("order.delivered", language);
        String orderFailed = MessageSourceUtils.getLocalizedMessage("order.failed", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(gotFromPlace, orderFailed), true, getBackMessage(language));
    }

    public static ReplyKeyboardMarkup deliverButtons(Language language) {
        String assigned = MessageSourceUtils.getLocalizedMessage("button.assigned", language);
        String activeOrder = MessageSourceUtils.getLocalizedMessage("button.active.order", language);
        String logOut = MessageSourceUtils.getLocalizedMessage("button.log.out", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(assigned, activeOrder, logOut), true);
    }

    public static ReplyKeyboardMarkup myOrderButtons(Language language) {
        String active = MessageSourceUtils.getLocalizedMessage("button.user.order.active", language);
        String archive = MessageSourceUtils.getLocalizedMessage("button.user.order.archive", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(active, archive), false, getBackMessage(language));
    }

    public static ReplyKeyboardMarkup orderManagementButtons(Language language) {
        String makeOrder = MessageSourceUtils.getLocalizedMessage("alert.make.order", language);
        String clearCart = MessageSourceUtils.getLocalizedMessage("alert.clear.cart", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(makeOrder, clearCart), false, getBackMessage(language));
    }

    public static ReplyKeyboardMarkup orderPlacementButtons(Language language) {
        String brand = MessageSourceUtils.getLocalizedMessage("button.select.brand", language);
        String cart = MessageSourceUtils.getLocalizedMessage("button.view.cart", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(brand, cart), true, getBackMessage(language));
    }

    public static ReplyKeyboardMarkup confirmationButtons(Language language) {
        String yes = MessageSourceUtils.getLocalizedMessage("button.yes", language);
        String no = MessageSourceUtils.getLocalizedMessage("button.no", language);
        return makeReplyKeyboardButtonsByStringsList(List.of(yes, no), true);
    }

    public static ReplyKeyboardMarkup viewBrandsButtons(Language language) {
        String backMessage = getBackMessage(language);
        String cartMessage = getCartMessage(language);
        return makeReplyKeyboardButtons(brandService.getAll(), false, backMessage);
    }

    public static ReplyKeyboardMarkup viewBrandCategoriesButtons(UUID brandID, Language language) {
        String backMessage = getBackMessage(language);
        return makeReplyKeyboardButtons(new ArrayList<>(categoryService.getBrandCategories(brandID)), true, backMessage);
    }

    private static <T extends BaseModel & Displayable> KeyboardButton makeButton(T t) {
        return new KeyboardButton(t.getDisplayName());
    }

    private static <T extends BaseModel & Displayable> ReplyKeyboardMarkup makeReplyKeyboardButtons(List<T> list, boolean oneTime, String... details) {
        List<KeyboardButton[]> rows = IntStream.range(0, list.size())
                .filter(index -> index % 2 == 0)
                .mapToObj(index -> list.subList(index, Math.min(index + 2, list.size()))
                        .stream()
                        .map(ReplyKeyboardMarkupFactory::makeButton)
                        .toArray(KeyboardButton[]::new))
                .collect(Collectors.toList());
        if (details != null && details.length > 0) {
            KeyboardButton[] detailButtons = Arrays.stream(details)
                    .map(KeyboardButton::new)
                    .toArray(KeyboardButton[]::new);
            rows.add(detailButtons);
        }
        KeyboardButton[][] keyboardArray = rows.toArray(new KeyboardButton[0][]);

        return new ReplyKeyboardMarkup(keyboardArray)
                .resizeKeyboard(true)
                .oneTimeKeyboard(oneTime);
    }

    private static ReplyKeyboardMarkup makeReplyKeyboardButtonsByStringsList(List<String> stringList, boolean oneTime, String... details) {
        List<KeyboardButton[]> rows = IntStream.range(0, stringList.size())
                .filter(index -> index % 2 == 0)
                .mapToObj(index -> stringList.subList(index, Math.min(index + 2, stringList.size()))
                        .stream()
                        .map(KeyboardButton::new)
                        .toArray(KeyboardButton[]::new))
                .collect(Collectors.toList());
        if (details != null && details.length > 0) {
            KeyboardButton[] detailButtons = Arrays.stream(details)
                    .map(KeyboardButton::new)
                    .toArray(KeyboardButton[]::new);
            rows.add(detailButtons);
        }
        KeyboardButton[][] keyboardArray = rows.toArray(new KeyboardButton[0][]);
        return new ReplyKeyboardMarkup(keyboardArray)
                .resizeKeyboard(true)
                .oneTimeKeyboard(oneTime);
    }

    private static String getBackMessage(@NotNull Language language) {
        return MessageSourceUtils.getLocalizedMessage("button.back", language);
    }

    private static String getCartMessage(@NotNull Language language) {
        return MessageSourceUtils.getLocalizedMessage("button.cart", language);
    }
}
