package com.pdp.utils.factory;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.service.customerOrderGeoPiont.CustomerOrderGeoPointService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.utils.source.StatusSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.branchLocation.BranchLocation;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.branchLocation.BranchLocationService;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:29
 **/
public class SendMessageFactory {
    private static final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private static final BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final BranchLocationService branchLocationService = ThreadSafeBeansContainer.branchLocationServiceThreadLocal.get();
    private static final CustomerOrderGeoPointService geoPointService = ThreadSafeBeansContainer.geoPointServiceThreadLocal.get();
    private static final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    private static final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    public static SendMessage createMessage(Object chatID, String messageText, Keyboard keyboardMarkup) {
        SendMessage message = new SendMessage(chatID, messageText);
        return message.replyMarkup(keyboardMarkup);
    }

    public static SendMessage sendMessageBackButton(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageCartIsEmpty(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("info.emptyCartMessage", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageNotArchiveOrders(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.not.archive", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageNoOrderForDeliverer(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.deliverer.no.order", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageOrderReceived(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("order.accepted", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.deliverButtons(language));
    }

    public static SendMessage sendMessageOrderCloser(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("order.already.got", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageNoOrderActiveForDeliverer(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.deliverer.no.order.active", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessagePaymentType(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.payment.type", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.paymentTypeButtons(language));
    }

    public static SendMessage sendMessageCartCleared(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.cart.cleaned", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderManagementButtons(language));
    }

    public static SendMessage sendMessageOrderManagementMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderManagementButtons(language));
    }

    public static SendMessage sendMessageNotActiveOrders(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.not.active", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.backButton(language));
    }

    public static SendMessage sendMessageSelectLanguageMenu(Object chatID) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.language", Language.EN);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.selectLangButtons());
    }

    public static SendMessage sendMessageWithUserMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.select.section", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.userButtons(language));
    }

    public static SendMessage sendMessageWithBrandsMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose.brand", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.viewBrandsButtons(language));
    }

    public static SendMessage sendMessageDeliverMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.select.section", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.deliverButtons(language));
    }

    public static SendMessage sendMessageOrderPlacementMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.select.section", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderPlacementButtons(language));

    }

    public static SendMessage sendMessageBrandCategoriesMenu(Object chatID, UUID brandID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.user.menu", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.viewBrandCategoriesButtons(brandID, language));
    }

    public static SendMessage sendMessageMyOderMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.myOrderButtons(language));
    }

    public static SendMessage sendMessageOrderProcess1(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderProcessButtons1(language));
    }

    public static SendMessage sendMessageOrderProcess2(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderProcessButtons2(language));
    }

    public static SendMessage sendMessageUpdateOrder(Object chatID, UUID orderID, Language language) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkupFactory.foodCounter(orderID);
        String localizedMessage = MessageSourceUtils.getLocalizedMessage("alert.quantity", language);
        return createMessage(chatID, localizedMessage, keyboardMarkup);
    }

    public static SendMessage sendMessageUpdateOrderCount(Object chatID, UUID orderID, String callbackData, Language language) {
        String action = callbackData.substring(0, 1);
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkupFactory.updateFoodCount(orderID, action);
        String quantity = MessageSourceUtils.getLocalizedMessage("alert.quantity.update", language);
        return createMessage(chatID, quantity, keyboardMarkup);
    }

    public static SendMessage sendMessageConfirmation(Object chatID, Language language) {
        String alert = MessageSourceUtils.getLocalizedMessage("alert.accept", language);
        return createMessage(chatID, alert, ReplyKeyboardMarkupFactory.confirmationButtons(language));

    }

    public static SendMessage sendMessageLocation(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.location", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.confirmLocationButton());
    }

    public static SendMessage sendMessageContact(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.contact", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.confirmContactButton());
    }

    public static SendMessage sendMessageEnterFullname(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.fullname", language);
        return new SendMessage(chatID, message);
    }

    public static List<SendMessage> sendMessagesOrdersInProcessForDeliverer(Object chatID, UUID delivererID, Language language) {
        List<CustomerOrder> processByDeliverer = customerOrderService.getOrdersInProcessByDeliverer(delivererID);
        return processByDeliverer.stream()
                .map(customerOrder -> {
                    String format = customerOrderFormatForDeliverer(customerOrder, language);
                    return new SendMessage(chatID, format);
                }).toList();
    }

    public static List<SendMessage> sendMessagesOrdersInProcessForUser(Object chatID, UUID userID, Language language) {
        List<CustomerOrder> processByDeliverer = customerOrderService.getOrdersInProcessByUser(userID);
        return processByDeliverer.stream()
                .map(customerOrder -> {
                    String format = customerOrderFormatForDeliverer(customerOrder, language);
                    return new SendMessage(chatID, format);
                }).toList();

    }

    public static List<SendMessage> sendMessagesOrdersToDeliverer(Object chatID, Language language) {
        List<CustomerOrder> pendingOrdersForDeliverer = customerOrderService.getPendingOrdersForDeliverer();
        return pendingOrdersForDeliverer.stream().map((customerOrder -> {
            String format = customerOrderFormatForDeliverer(customerOrder, language);
            return createMessage(chatID, format, InlineKeyboardMarkupFactory.checkMarkButton(customerOrder));
        })).toList();
    }

    public static List<SendMessage> sendMessagesUserArchive(Object chatID, UUID userID, Language language) {
        List<CustomerOrder> archiveOrders = customerOrderService.getArchive(userID);
        return archiveOrders.stream().map(customerOrder -> {
            List<Order> orders = orderService.getOrdersByCustomerID(customerOrder.getId());
            String format = customerOrderFormatForUser(orders, customerOrder, language);
            return new SendMessage(chatID, format);
        }).toList();
    }

    public static List<SendMessage> sendMessageNewOrderToDeliverer(CustomerOrder customerOrder, Language language) {
        String format = customerOrderFormatForDeliverer(customerOrder, language);
        List<TelegramUser> telegramUserByState = telegramUserService.getTelegramUserByState(DeliveryMenuState.VIEW_ASSIGNED_ORDERS);
        return telegramUserByState.stream()
                .map(telegramUser -> createMessage(telegramUser.getChatID(), format, InlineKeyboardMarkupFactory.checkMarkButton(customerOrder)))
                .toList();
    }

    public static SendMessage sendMessageUserOrderNotConfirmed(Object chatID, UUID id, Language language) {
        CustomerOrder notConfirmedOrder = customerOrderService.getNotConfirmedOrder(id);
        List<Order> orders = orderService.getOrdersByCustomerID(notConfirmedOrder.getId());
        String format = customerOrderFormatForUser(orders, notConfirmedOrder, language);
        return createMessage(chatID, format, ReplyKeyboardMarkupFactory.orderManagementButtons(language));
    }

    private static String customerOrderFormatForDeliverer(CustomerOrder customerOrder, Language language) {
        Branch branch = getBranch(customerOrder);
        Brand brand = getBrand(branch);
        BranchLocation branchLocation = getBranchLocation(branch);
        CustomerOrderGeoPoint orderGeoPoint = getGeoPoint(customerOrder);
        String status = StatusSourceUtils.getLocalizedStatus(customerOrder.getOrderStatus(), language);
        return String.format(
                "\uD83C\uDFE2 Brand: %s\n⬆\uFE0F From location: %s\n ⬇\uFE0FTo location: %s\n \uD83D\uDCB0Price: %s\n \uD83D\uDCB5Payment Type: %s\n \uD83D\uDCCAStatus: %s",
                brand.getName(),
                branchLocation.getLatidue() + " - " + branchLocation.getLongtidue(),
                orderGeoPoint.getLattidue() + " - " + orderGeoPoint.getLongtidue(),
                customerOrder.getOrderPrice(),
                customerOrder.getPaymentType(),
                status
        );
    }

    private static String customerOrderFormatForUser(List<Order> orders, CustomerOrder customerOrder, Language language) {
        Branch branch = getBranch(customerOrder);
        Brand brand = getBrand(branch);
//        CustomerOrderGeoPoint orderGeoPoint = getGeoPoint(customerOrder);
        String status = StatusSourceUtils.getLocalizedStatus(customerOrder.getOrderStatus(), language);
        StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append(String.format("\uD83C\uDFE2 Brand: %s\n", brand.getName()));
//        formatBuilder.append(String.format("To location: %s\n", orderGeoPoint.getLattidue() + " - " + orderGeoPoint.getLongtidue()));
        formatBuilder.append(String.format("\uD83D\uDCB0 Price: %s\n", customerOrder.getOrderPrice()));
        formatBuilder.append(String.format("\uD83D\uDCB5 Payment Type: %s\n", customerOrder.getPaymentType()));
        formatBuilder.append(String.format("\uD83D\uDCCA Status: %s\n", status));

        for (int i = 0; i < orders.size(); i++) {
            Food food = foodService.getByID(orders.get(i).getFoodID());
            Order order = orders.get(i);
            formatBuilder.append(String.format("\uD83C\uDF5F " + (i + 1) + " - %s: %d x %.2f\n", food.getName(), order.getFoodQuantity(), order.getFoodPrice()));
        }
        return formatBuilder.toString();
    }

    private static Branch getBranch(CustomerOrder customerOrder) {
        return branchService.getByID(customerOrder.getBranchID());
    }

    private static Brand getBrand(Branch branch) {
        return brandService.getByID(branch.getBrandID());
    }

    private static BranchLocation getBranchLocation(Branch branch) {
        return branchLocationService.getByID(branch.getLocationID());
    }

    private static CustomerOrderGeoPoint getGeoPoint(CustomerOrder customerOrder) {
        return geoPointService.getByID(customerOrder.getCustomerOrderGeoPointID());
    }
}
