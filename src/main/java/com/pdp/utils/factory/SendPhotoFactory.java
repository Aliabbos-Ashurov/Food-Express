package com.pdp.utils.factory;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.category.Category;
import com.pdp.web.model.description.Description;
import com.pdp.web.model.food.Food;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.category.CategoryService;
import com.pdp.web.service.description.DescriptionService;
import com.pdp.web.service.food.FoodService;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.io.File;
import java.util.Set;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  17:36
 **/
public class SendPhotoFactory {
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final DescriptionService descriptionService = ThreadSafeBeansContainer.descriptionServiceThreadLocal.get();
    private static final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static final CategoryService categoryService = ThreadSafeBeansContainer.categoryServiceThreadLocal.get();

    public static SendPhoto sendPhotoCategoryWithFoodsButton(Object chatID, UUID brandID, String categoryName) {
        Category category = categoryService.getCategoryByBrandID(brandID, categoryName);
        SendPhoto sendPhoto = new SendPhoto(chatID, category.getImageUrl());
        return sendPhoto.replyMarkup(InlineKeyboardMarkupFactory.viewFoods(brandID, categoryName));
    }

    public static SendPhoto sendPhotoBrandWithDescription(Object chatID, UUID brandID) {
        Brand brand = brandService.getByID(brandID);
        Description description = descriptionService.getByID(brand.getDescriptionID());
        SendPhoto sendPhoto = new SendPhoto(chatID, brand.getImageURL());
        return sendPhoto.caption(description.getText());
    }

    public static SendPhoto sendPhotoFoodWithDescription(Object chatID, UUID foodID) {
        Food food = foodService.getByID(foodID);
        Description description = descriptionService.getByID(food.getDescriptionID());
        SendPhoto sendPhoto = new SendPhoto(chatID, food.getImageUrl());
        return sendPhoto.caption(description.getText());
    }
}
