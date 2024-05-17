package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.category.CategoryService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:06
 **/
public class UserViewMessageProcessor implements Processor<UserViewState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    private final CategoryService categoryService = ThreadSafeBeansContainer.categoryServiceThreadLocal.get();
    private final FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();
    private final BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();

    @Override
    public void process(Update update, UserViewState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        String text = message.text();
        switch (state) {
            case VIEW_BRANDS -> {
                handleViewBrands(text, chatID);
            }
            case VIEW_CATEGORIES -> {
                TelegramUser telegramUser = getTelegramUser(chatID);
                CustomerOrder customerOrder = customerOrderService.getByUserID(telegramUser.getId());
                UUID branchID = customerOrder.getBranchID();
                Branch branch = branchService.getByID(branchID);
                UUID brandID = branch.getBrandID();
            }
            case VIEW_FOODS -> {
            }
            case COUNT -> {
            }
        }
    }

    private void handleViewBrands(String text, Long chatID) {
        if (checkLocalizedMessage(text, "button.back", chatID)) {
            updateTelegramUserState(chatID, UserMenuOptionState.PLACE_ORDER);
            bot.execute(SendMessageFactory.sendMessageOrderPlacementMenu(chatID, getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage(text, "button.cart", chatID)) {

        } else {
            Brand brand = brandService.getBrandByName(text);
            if (brand == null) {
                invalidSelectionSender(chatID);
                return;
            }
            updateTelegramUserState(chatID, UserViewState.VIEW_CATEGORIES);
            TelegramUser telegramUser = getTelegramUser(chatID);
            UUID brandID = brand.getId();
            CustomerOrder customerOrder = customerOrderService.getOrCreate(telegramUser.getId(), brandID);
            bot.execute(SendMessageFactory.sendMessageBrandCategoriesMenu(chatID, brandID, getTelegramUserLanguage(chatID)));
        }
    }

    private TelegramUser updateTelegramUserState(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
        return telegramUser;
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
