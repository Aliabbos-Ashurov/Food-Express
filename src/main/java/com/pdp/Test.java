package com.pdp;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.factory.SendPhotoFactory;
import com.pdp.web.service.address.AddressService;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.branchLocation.BranchLocationService;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.category.CategoryService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.deliverer.DelivererService;
import com.pdp.web.service.description.DescriptionService;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.web.service.order.OrderService;
import com.pdp.web.service.transport.TransportService;
import com.pdp.web.service.user.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 30/April/2024  21:09
 **/
public class Test {
    private static final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private static final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    private static final AddressService addressService = ThreadSafeBeansContainer.addressServiceThreadLocal.get();
    private static final BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static final CategoryService categoryService = ThreadSafeBeansContainer.categoryServiceThreadLocal.get();
    private static final FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();
    private static final DescriptionService descriptionService = ThreadSafeBeansContainer.descriptionServiceThreadLocal.get();
    private static final DelivererService delivererService = ThreadSafeBeansContainer.delivererServiceThreadLocal.get();
    private static final TransportService transportService = ThreadSafeBeansContainer.transportServiceThreadLocal.get();
    private static final UserService userService = ThreadSafeBeansContainer.userServiceThreadLocal.get();
    private static final BranchLocationService branchLocationService = ThreadSafeBeansContainer.branchLocationServiceThreadLocal.get();
    private static final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private static final TelegramBot telegramBot = TelegramBotConfiguration.get();

    public static void main(String[] args) {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                Message message = update.message();
                if (message != null) {
                    User from = message.from();
                    Long id = from.id();
                    telegramBot.execute(SendPhotoFactory.sendPhotoCategoryWithFoodsButton(id, UUID.fromString("f60fc7f6-930b-4d00-ad64-2526e1459bce"), "LANCHBOKS"));
                } else {
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static void fillDefaultObjects() {
        ////-------------------------------------------
        ////-------------------------------------------
        ////-------------------------------------------
       /* UUID KFC = UUID.fromString("f60fc7f6-930b-4d00-ad64-2526e1459bce");
        UUID BON = UUID.fromString("75cb24b7-8404-4cae-b8cc-bb14307185a1");
        UUID EVOS = UUID.fromString("8cecd430-ec65-427d-9fc7-c6e2b7ebd13c");
        UUID OQTEPA = UUID.fromString("01188cf2-3ccf-4692-9e01-7859e65fc46d");


        Category category = new Category("BASKET", KFC, "AgACAgIAAxkBAAMlZkLsaM6plKYNmaxLEG7JJlHQkuMAAljWMRsfPBlK7pqpv98GDU4BAAMCAAN4AAM1BA");


        Description description = new Description("INFO", "Achchiq panirovkadagi 26 ta tovuq qanotchalari");
        Food food = Food.builder()
                .name("Basket L – 26 achchiq qanot")
                .price(new BigDecimal("130000"))
                .descriptionID(description.getId())
                .categoryID(category.getId())
                .imageUrl("AgACAgIAAxkBAAMzZkLuI2F5pL4B_Z_wmrKCNQ1EaEsAAmPWMRsfPBlKQBH9qx9GOGkBAAMCAAN4AAM1BA")
                .build();


        FoodBrandMapping foodBrandMapping = new FoodBrandMapping(category.getName(), food.getId(), KFC);

        foodService.add(food);
        descriptionService.add(description);
        categoryService.add(category);
        foodBrandMappingService.add(foodBrandMapping);*/
    }
}
