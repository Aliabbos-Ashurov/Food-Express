package com.pdp.telegram.processor.callback.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.CustomOrderDTO;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramUser.ConfirmOrderState;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pdp.utils.factory.InlineKeyboardMarkupFactory;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.factory.SendPhotoFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:06
 **/
public class UserViewCallbackProcessor implements Processor<UserViewState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();

    @Override
    public void process(Update update, UserViewState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = (Message) callbackQuery.maybeInaccessibleMessage();
        Long chatID = message.chat().id();
        String data = callbackQuery.data();
        if (state.equals(UserViewState.VIEW_FOODS)) {
            handlerViewFoods(chatID, data);
        } else if (state.equals(UserViewState.COUNT)) {
            handleCountFoods(chatID, data, message.messageId());
        }
    }

    private void handleCountFoods(Long chatID, String data, Integer messageID) {
        if (Objects.equals(data.substring(0, 7), "nothing")) return;
        if (Objects.equals(data.substring(0, 5), "price")) {
            updateTelegramUser(chatID, DefaultState.BASE_USER_MENU);
            Order order = orderService.getByID(UUID.fromString(data.substring(5)));
            CustomerOrder customerOrder = customerOrderService.getNotConfirmedOrder(getTelegramUser(chatID).getId());
            customerOrder.setOrderPrice(order.getFoodPrice());
            customerOrderService.update(customerOrder);
            bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("alert.product.on.cart", getTelegramUserLanguage(chatID))));
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
            return;
        }
        String text = data.substring(0, 1);
        if (data.length() < 35) {
            invalidSelectionSender(chatID);
            return;
        }
        data = data.substring(1);
        Order order = orderService.getByID(UUID.fromString(data));
        if (Objects.isNull(order)) {
            invalidSelectionSender(chatID);
            return;
        }
        int quantity = order.getFoodQuantity();
        if (Objects.equals(text, "-") && quantity > 1) {
            updateOrder(order, quantity, true);
        } else if (Objects.equals(text, "+")) {
            updateOrder(order, quantity, false);
        }
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(chatID, messageID)
                .replyMarkup(InlineKeyboardMarkupFactory.foodCounter(order.getId()));
        bot.execute(editMessageReplyMarkup);
    }

    private void updateOrder(Order order, int quantity, boolean check) {
        BigDecimal price = foodService.getByID(order.getFoodID()).getPrice();
        if (check) quantity -= 1;
        else quantity += 1;
        BigDecimal foodPrice = price.multiply(BigDecimal.valueOf(quantity));
        order.setFoodQuantity(quantity);
        order.setFoodPrice(foodPrice);
        orderService.update(order);
    }

    private void handlerViewFoods(Long chatID, String data) {
        Food food = foodService.getByID(UUID.fromString(data));
        updateTelegramUser(chatID, UserViewState.COUNT);
        CustomerOrder customerOrder = customerOrderService.getByUserID(getTelegramUser(chatID).getId());
        UUID customerOrderID = customerOrder.getId();
        CustomOrderDTO customOrderDTO = new CustomOrderDTO(getTelegramUser(chatID).getId(), customerOrder.getBranchID());
        Order buildOrder = buildOrder(customerOrderID, food);
        Order order = orderService.getOrCreate(customOrderDTO, buildOrder);
        bot.execute(SendPhotoFactory.sendPhotoFoodWithDescription(chatID, food.getId()));
        bot.execute(SendMessageFactory.sendMessageUpdateOrder(chatID, order.getId(), getTelegramUserLanguage(chatID)));
    }

    private static Order buildOrder(UUID customerOrderID, Food food) {
        return Order.builder()
                .customerOrderID(customerOrderID)
                .foodID(food.getId())
                .foodQuantity(1)
                .foodPrice(food.getPrice())
                .build();
    }

    private void updateTelegramUser(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
    }

    private TelegramUser getTelegramUser(Long chatID) {
        return telegramUserService.findByChatID(chatID);
    }

    private Language getTelegramUserLanguage(Long chatID) {
        return getTelegramUser(chatID).getLanguage();
    }

    private boolean checkLocalizedMessage(String message, String key, Long chatID) {
        String localizedMessage = MessageSourceUtils.getLocalizedMessage(key, getTelegramUserLanguage(chatID));
        return Objects.equals(localizedMessage, message);
    }

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }
}
