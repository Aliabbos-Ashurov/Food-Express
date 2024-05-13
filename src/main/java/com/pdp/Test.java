package com.pdp;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.config.imagesPath.BrandImagesPath;
import com.pdp.controller.UserController;
import com.pdp.utils.MenuUtils;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.branchLocation.BranchLocation;
import com.pdp.web.model.brand.Brand;
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
import java.time.LocalDateTime;
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

    public static void main(String[] args) {
        UserController.startApplication();
        //fillDefaultObjects();
    }

    private static void fillDefaultObjects() {
        ////-------------------------------------------
        ////-------------------------------------------
        ////-------------------------------------------
        /*String KFC = "f60fc7f6-930b-4d00-ad64-2526e1459bce";
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
        foodBrandMappingService.add(foodBrandMapping);*/

        ////-------------------------------------------
        ////-------------------------------------------
        ////-------------------------------------------

        /*Description kfcDesc = new Description("Faoliyat", "Yetkazish: 0 soʻmdan boshlab\n" +
                "Yetkazish vaqti: 35-45 min");
        Description evosDesc = new Description("Faoliyat", "Yetkazish: 0 soʻmdan boshlab\n" +
                "Yetkazish vaqti: 35-45 min");
        Description bonDecs = new Description("Faoliyat","Yetkazish: 0 soʻmdan boshlab\n" +
                "Yetkazish vaqti: 35-45 min");
        Description oqtepaDesc = new Description("Faoliyat", "Yetkazish: 0 soʻmdan boshlab\n" +
                "Yetkazish vaqti: 30-40 min");
        descriptionService.add(kfcDesc);
        descriptionService.add(evosDesc);
        descriptionService.add(bonDecs);
        descriptionService.add(oqtepaDesc);

        // --------------

        Brand kfc = new Brand("KFC", kfcDesc.getId(),
                "AgACAgIAAxkBAAID-WY5-CZ2V8CgHpcpPFqFGqVGZrxeAAJk2TEbAAGP0UkraG0HtByhGAEAAwIAA3MAAzUE", LocalDateTime.now(), LocalDateTime.now());
        Brand bon = new Brand("Bon!",bonDecs.getId(),
                "AgACAgIAAxkBAAIEAmY5-Ql_vyD_oxrELna5Zlp93pSTAAJ32TEbAAGP0Ulxwb1wCgFa3AEAAwIAA3gAAzUE",LocalDateTime.now(),LocalDateTime.now());
        Brand evos = new Brand("Evos",evosDesc.getId(),
                "AgACAgIAAxkBAAIEAAFmOfj640S_OtZiLj9ZOlqSIIyTbgACddkxGwABj9FJ3g97j6jUKowBAAMCAANzAAM1BA",LocalDateTime.now(),LocalDateTime.now());
        Brand oqtepaLavash = new Brand("Oq-Tepa Lavash", oqtepaDesc.getId(),
                "AgACAgIAAxkBAAIEBGY5-RfermaSOydz-X1WRz_ez7CZAAJ42TEbAAGP0UnTr3Jsj97ayQEAAwIAA3kAAzUE",LocalDateTime.now(),LocalDateTime.now());
        brandService.add(kfc);
        brandService.add(bon);
        brandService.add(evos);
        brandService.add(oqtepaLavash);*/

/*
        BranchLocation kfcLocation = new BranchLocation(null,41.31201655943021, 69.29026205881249);
        BranchLocation bonLocation = new BranchLocation(null,41.30991332057316, 69.2916847126298);
        BranchLocation evosBranchLocation = new BranchLocation(null, 41.32650452028879, 69.25337075886324);
        BranchLocation oqtepaBranchLocation = new BranchLocation(null, 41.32314498615789, 69.23414373069218);

        branchLocationService.add(kfcLocation);
        branchLocationService.add(bonLocation);
        branchLocationService.add(evosBranchLocation);
        branchLocationService.add(oqtepaBranchLocation);

        Branch kfcbranch = new Branch(UUID.fromString("f60fc7f6-930b-4d00-ad64-2526e1459bce"),kfcLocation.getId(),"+998781297000");
        Branch bonBranch = new Branch(UUID.fromString("75cb24b7-8404-4cae-b8cc-bb14307185a1"),bonLocation.getId(),"+998712320008");
        Branch evosBranch = new Branch(UUID.fromString("8cecd430-ec65-427d-9fc7-c6e2b7ebd13c"),evosBranchLocation.getId(),"+998712031212");
        Branch oqtepaBranch = new Branch(UUID.fromString("01188cf2-3ccf-4692-9e01-7859e65fc46d"),oqtepaBranchLocation.getId(),"+998781500030");

        branchService.add(kfcbranch);
        branchService.add(bonBranch);
        branchService.add(evosBranch);
        branchService.add(oqtepaBranch);*/
    }
}
