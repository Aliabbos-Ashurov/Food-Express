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
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.NonNull;

import java.util.UUID;

/**
 * Factory class to create SendPhoto requests for Telegram Bot API with various customizations.
 * This class provides methods to create SendPhoto requests with specific configurations
 * such as inline keyboards and captions based on the application data models.
 * <p>
 * Utilizes services for accessing data and a thread-safe container for service instances.
 *
 * @see com.pdp.config.ThreadSafeBeansContainer
 * @since 14/May/2024
 */
public class SendPhotoFactory {
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final DescriptionService descriptionService = ThreadSafeBeansContainer.descriptionServiceThreadLocal.get();
    private static final FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static final CategoryService categoryService = ThreadSafeBeansContainer.categoryServiceThreadLocal.get();

    /**
     * Creates a {@link SendPhoto} request for a given category and its associated foods.
     *
     * @param chatID       The chat identifier to which the photo will be sent.
     * @param brandID      The unique identifier of the brand for which to get the category.
     * @param categoryName The name of the category to get.
     * @return A configured {@link SendPhoto} request object ready for sending a photo.
     */
    public static SendPhoto sendPhotoCategoryWithFoodsButton(@NonNull Object chatID, @NonNull UUID brandID, @NonNull String categoryName) {
        Category category = categoryService.getCategoryByBrandID(brandID, categoryName);
        SendPhoto sendPhoto = new SendPhoto(chatID, category.getImageUrl());
        return sendPhoto.replyMarkup(InlineKeyboardMarkupFactory.viewFoods(brandID, categoryName));
    }

    /**
     * Creates a {@link SendPhoto} request for a given brand along with its description.
     *
     * @param chatID  The chat identifier for sending the photo.
     * @param brandID The unique identifier of the brand for which to retrieve the description.
     * @return A {@link SendPhoto} request with the brand's image and description as caption.
     */
    public static SendPhoto sendPhotoBrandWithDescription(@NonNull Object chatID, @NonNull UUID brandID) {
        Brand brand = brandService.getByID(brandID);
        Description description = descriptionService.getByID(brand.getDescriptionID());
        SendPhoto sendPhoto = new SendPhoto(chatID, brand.getImageURL());
        return sendPhoto.caption(description.getText());
    }

    /**
     * Creates a {@link SendPhoto} request for a specific food item along with its description.
     *
     * @param chatID The chat identifier for sending the photo.
     * @param foodID The unique identifier of the food item to retrieve.
     * @return A {@link SendPhoto} request with the food's image and description as caption.
     */
    public static SendPhoto sendPhotoFoodWithDescription(@NonNull Object chatID, @NonNull UUID foodID) {
        Food food = foodService.getByID(foodID);
        Description description = descriptionService.getByID(food.getDescriptionID());
        SendPhoto sendPhoto = new SendPhoto(chatID, food.getImageUrl());
        return sendPhoto.caption(description.getText());
    }
}
