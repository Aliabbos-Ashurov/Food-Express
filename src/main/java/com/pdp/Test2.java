package com.pdp;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.controller.UserController;
import com.pdp.web.model.category.Category;
import com.pdp.web.model.description.Description;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
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

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 30/April/2024  21:09
 **/
public class Test2 {
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

    public static void main(String[] args) {
        UserController.startApplication();
        //fillDefaultObjects();
    }

    private static void fillDefaultObjects() {
        ////-------------------------------------------
        ////-------------------------------------------
        ////-------------------------------------------
        String KFC = "f60fc7f6-930b-4d00-ad64-2526e1459bce";
        String BON = "75cb24b7-8404-4cae-b8cc-bb14307185a1";
        String EVOS = "8cecd430-ec65-427d-9fc7-c6e2b7ebd13c";
        String OQTEPA = "01188cf2-3ccf-4692-9e01-7859e65fc46d";



        Description description = new Description("Ma'lumot","Pishloqli, original sous");
        Category category = new Category("SOUS",UUID.fromString(KFC),"AgACAgIAAxkBAAIEOWZA46LJZU3A5Drfx68TYNvLWISTAAKD2jEb4IoBSiFPwmTpm5TFAQADAgADeAADNQQ");
        Food food = Food.builder()
                .name("Pishloqli, original sous")
                .price(new BigDecimal("4000"))
                .descriptionID(description.getId())
                .categoryID(category.getId())
                .build();


        FoodBrandMapping foodBrandMapping = new FoodBrandMapping(category.getName(),food.getId(),UUID.fromString(KFC));

        foodService.add(food);
        descriptionService.add(description);
        categoryService.add(category);
        foodBrandMappingService.add(foodBrandMapping);
    }
}
