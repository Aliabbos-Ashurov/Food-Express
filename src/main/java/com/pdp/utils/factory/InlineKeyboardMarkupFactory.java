package com.pdp.utils.factory;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.java.console.support.Displayable;
import com.pdp.web.model.BaseModel;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:29
 **/
public class InlineKeyboardMarkupFactory {
    private static final FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();
    private static final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    public static InlineKeyboardMarkup checkMarkButton(CustomerOrder customerOrder) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("✅");
        inlineKeyboardButton.callbackData(String.valueOf(customerOrder.getId()));
        return new InlineKeyboardMarkup(inlineKeyboardButton);
    }

    public static InlineKeyboardMarkup viewFoods(UUID brandID, String categoryName) {
        List<FoodBrandMapping> foodBrandMappings = foodBrandMappingService.getBrandFoodsByCategoryName(brandID, categoryName);
        List<Food> foods = foodBrandMappings.stream()
                .map(foodBrandMapping -> foodService.getByID(foodBrandMapping.getFoodID()))
                .toList();
        return makeInlineKeyboardButtons(foods);
    }

    private static BigDecimal calculateNewPrice(int quantity, BigDecimal totalPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        return totalPrice.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static <T extends BaseModel & Displayable> InlineKeyboardButton makeInlineKeyboardButton(T t, int num) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(String.valueOf(num));
        return inlineKeyboardButton.callbackData(String.valueOf(t.getId()));
    }

    private static <T extends BaseModel & Displayable> InlineKeyboardMarkup makeInlineKeyboardButtons(List<T> list) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < list.size(); i += 3) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = i; j < i + 3 && j < list.size(); j++) {
                row.add(makeInlineKeyboardButton(list.get(j), j + 1));
            }
            rows.add(row);
        }
        InlineKeyboardButton[][] keyboardArray = rows.stream()
                .map(row -> row.toArray(new InlineKeyboardButton[0]))
                .toArray(InlineKeyboardButton[][]::new);
        return new InlineKeyboardMarkup(keyboardArray);
    }

    private static InlineKeyboardButton createInlineButton(String text, UUID orderID, String dataPrefix) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.callbackData(dataPrefix + orderID.toString());
        return button;
    }

    public static InlineKeyboardMarkup foodCounter2(UUID orderID) {
        Order order = orderService.getByID(orderID);
        InlineKeyboardButton minus = new InlineKeyboardButton(" ➖ ");
        minus.callbackData("-" + orderID);
        InlineKeyboardButton plus = new InlineKeyboardButton(" ➕ ");
        plus.callbackData("+" + orderID);
        InlineKeyboardButton price = new InlineKeyboardButton(String.format("$%.2f", order.getFoodPrice()));
        price.callbackData("ACCEPT" + orderID);
        InlineKeyboardButton foodQuantity = new InlineKeyboardButton(String.valueOf(order.getFoodQuantity()));
        foodQuantity.callbackData("NOTHING" + orderID);

        return new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {minus, foodQuantity, plus},
                {price}
        });

    }

    public static InlineKeyboardMarkup updateFoodCount(String callbackQueryData) {
        String action = callbackQueryData.substring(1);
        UUID orderID = UUID.fromString(action);

        Order order = orderService.getByID(orderID);
        int currentQuantity = order.getFoodQuantity();
        BigDecimal currentPrice = order.getFoodPrice();
        BigDecimal singleItemPrice = currentPrice.divide(BigDecimal.valueOf(currentQuantity), 2, RoundingMode.HALF_UP);
        if ("+".equals(action)) {
            currentQuantity += 1;
        } else if ("-".equals(action) && currentQuantity > 1) {
            currentQuantity -= 1;
        }

        BigDecimal newPrice = singleItemPrice.multiply(BigDecimal.valueOf(currentQuantity)).setScale(2, RoundingMode.HALF_UP);

        order.setFoodQuantity(currentQuantity);
        order.setFoodPrice(newPrice);
        orderService.update(order);

        return foodCounter(orderID);
    }

    public static InlineKeyboardMarkup foodCounter(UUID orderID) {
        Order order = orderService.getByID(orderID);
        InlineKeyboardButton minusButton = createInlineButton(" ➖ ", orderID, "-");
        InlineKeyboardButton plusButton = createInlineButton(" ➕ ", orderID, "+");
        InlineKeyboardButton priceButton = createInlineButton(String.format("\uD83D\uDED2%.2f", order.getFoodPrice()), orderID, "price");
        InlineKeyboardButton quantityButton = createInlineButton(String.valueOf(order.getFoodQuantity()), orderID, "quantity");

        return new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {minusButton, quantityButton, plusButton},
                {priceButton}
        });
    }

    public static InlineKeyboardMarkup updateFoodCount(UUID orderID, String action) {
        Order order = orderService.getByID(orderID);
        int currentQuantity = order.getFoodQuantity();
        if ("+".equals(action)) {
            order.setFoodQuantity(currentQuantity + 1);
        } else if ("-".equals(action)) {
            order.setFoodQuantity(currentQuantity > 1 ? currentQuantity - 1 : 1);
        }

        BigDecimal newPrice = calculateNewPrice(order.getFoodQuantity(), order.getFoodPrice());
        order.setFoodPrice(newPrice);
        orderService.update(order);

        return foodCounter(orderID);
    }
}

