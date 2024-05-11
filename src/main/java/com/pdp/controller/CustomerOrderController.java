package com.pdp.controller;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.CustomOrderDTO;
import com.pdp.java.console.ListUtils;
import com.pdp.java.console.NotificationHandler;
import com.pdp.java.console.Scan;
import com.pdp.utils.MenuUtils;
import com.pdp.utils.Utils;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.role.Role;
import com.pdp.web.model.address.Address;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.category.Category;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.deliverer.Deliverer;
import com.pdp.web.model.description.Description;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.model.order.Order;
import com.pdp.web.model.transport.Transport;
import com.pdp.web.model.user.User;
import com.pdp.web.service.address.AddressService;
import com.pdp.web.service.branch.BranchService;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 11/May/2024  14:41
 **/
public class CustomerOrderController {
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

    public static void start() {
        while (true) {
            displayUserMenu();
            switch (Scan.scanInt()) {
                case 1 -> placeOrder();
                case 2 -> myOrder();
                case 3 -> registerAsCourier();
                case 0 -> {
                    return;
                }
                default -> printInvalidSelectionError();
            }
        }
    }

    private static void placeOrder() {
        while (true) {
            displayDepartmentOrderMenu();
            switch (Scan.scanInt()) {
                case 1 -> BrandSelectionProcess();
                case 2 -> cart();
                case 0 -> {
                    return;
                }
                default -> printInvalidSelectionError();
            }
        }
    }
    private static void cart() {
        CustomerOrder notConfirmedOrder = customerOrderService.getNotConfirmedOrder(getCurrentUser().getId());
        List<Order> orders = orderService.getOdersByCustomerID(notConfirmedOrder.getId());
        orders.forEach(CustomerOrderController::displayOrderDetails);
    }

    private static void registerAsCourier() {
        System.out.println(MessageSourceUtils.getLocalizedMessage("main.menu.login", getCurrentUser().getLanguage()));
        String phoneNumber = Scan.scanStr("Enter phone number");
        Deliverer deliverer = new Deliverer(getCurrentUser().getFullname(), phoneNumber);
        Transport transport = Transport.builder()
                .deliverID(deliverer.getId())
                .name(Scan.scanStr("Enter transport name"))
                .registeredNumber("Enter registered number")
                .build();
        User currentUser = getCurrentUser();
        currentUser.setRole(Role.DELIVERER);
        boolean updated = userService.update(currentUser);
        boolean isDelivererSuccesfull = delivererService.add(deliverer);
        boolean isTransportSuccesfull = transportService.add(transport);
        NotificationHandler.notifyAction("Deliverer", "added", updated && isDelivererSuccesfull && isTransportSuccesfull);
    }

    private static void BrandSelectionProcess() {
        Brand brand = selectBrand();
        if (Objects.isNull(brand)) return;
        Category category = selectCategory(brand.getId());
        if (Objects.isNull(category)) return;
        Food food = selectFood(brand.getId(), category.getName());
        if (Objects.nonNull(food)) submitFoodOrder(food, brand);
    }

    private static void submitFoodOrder(Food food, Brand brand) {
        displayFoodDetails(food);
        displayConfirmationMenu();
        int scanned = Scan.scanInt();
        if (scanned == 1) {
            int quantity = Scan.scanInt("Enter quantity");
            BigDecimal orderPrice = food.getPrice().multiply(BigDecimal.valueOf(quantity));
            Branch branch = branchService.getBrandBranches(brand.getId()).getFirst();
            CustomerOrder customerOrder = customerOrderService.getOrCreate(getCurrentUser().getId(), branch.getId());
            CustomOrderDTO dto = new CustomOrderDTO(customerOrder.getUserID(), customerOrder.getBranchID());
            Order order = new Order(food.getId(), orderPrice, quantity, customerOrder.getId());
            Order serviceOrCreate = orderService.getOrCreate(dto, order);
            NotificationHandler.notifyAction("Order","added",Objects.nonNull(serviceOrCreate));
            MessageSourceUtils.getLocalizedMessage("success.itemAddedToCart", getCurrentUser().getLanguage());
        }
    }

    private static Brand selectBrand() {
        List<Brand> brands = brandService.getAll();
        NotificationHandler.checkData(brands);
        if (ListUtils.checkDataForNotNull(brands)) {
            ListUtils.displayListByName(brands);
            return Utils.getElementByIndex(brands, Scan.scanInt());
        }
        return null;
    }

    private static Category selectCategory(UUID brandID) {
        List<Category> categories = categoryService.getBrandCategories(brandID);
        NotificationHandler.checkData(categories);
        if (ListUtils.checkDataForNotNull(categories)) {
            ListUtils.displayListByName(categories);
            return Utils.getElementByIndex(categories, Scan.scanInt());
        }
        return null;
    }

    private static Food selectFood(UUID brandID, String categoryName) {
        List<FoodBrandMapping> foodBrandMappings = foodBrandMappingService.getBrandFoodsByCategoryName(brandID, categoryName);
        if (ListUtils.checkDataForNotNull(foodBrandMappings)) {
            List<Food> foods = getFoodsFromMapping(foodBrandMappings);
            if (ListUtils.checkDataForNotNull(foods)) {
                NotificationHandler.checkData(foods);
                ListUtils.displayListByName(foods);
                return Utils.getElementByIndex(foods, Scan.scanInt());
            }
        }
        return null;
    }

    private static List<Food> getFoodsFromMapping(List<FoodBrandMapping> list) {
        return list.stream()
                .map(foodBrandMapping -> foodService.getByID(foodBrandMapping.getFoodID()))
                .toList();
    }

    private static void myOrder() {
        while (true) {
            displayUserOrderMenu();
            switch (Scan.scanInt()) {
                case 1 -> showOrdersInProcess();
                case 2 -> showArchive();
                case 0 -> {
                    return;
                }
                default -> printInvalidSelectionError();
            }
        }
    }

    private static void showOrdersInProcess() {
        List<CustomerOrder> ordersInProcessByUser = customerOrderService.getOrdersInProcessByUser(getCurrentUser().getId());
        displayOrders(ordersInProcessByUser);
    }

    private static void showArchive() {
        List<CustomerOrder> customerOrdersArchive = customerOrderService.getArchive(getCurrentUser().getId());
        displayOrders(customerOrdersArchive);
    }

    private static void displayOrders(List<CustomerOrder> orders) {
        for (CustomerOrder customerOrder : orders) {
            List<Order> orderList = orderService.getOdersByCustomerID(customerOrder.getId());
            if (!orderList.isEmpty()) {
                Address address = addressService.getByID(customerOrder.getAddressID());
                System.out.println("------------------------------");
                System.out.println("Address:");
                System.out.println("City: " + address.getCity());
                System.out.println("Street: " + address.getStreet());
                System.out.println("Order Status: " + customerOrder.getOrderStatus());
                orderList.forEach(CustomerOrderController::displayOrderDetails);
                System.out.println("Finally Price: 0");
                System.out.println("------------------------------");
            }
        }
    }

    private static void displayBrandDescription(Brand brand) {
        Description description = descriptionService.getByID(brand.getDescriptionID());
        System.out.println("------------------------------");
        System.out.println(brand.getName());
        System.out.println(description.getName());
        System.out.println("------------------------------");
    }

    private static void displayFoodDetails(Food food) {
        Description description = descriptionService.getByID(food.getDescriptionID());
        System.out.println("--------------");
        System.out.println("Food: " + food.getName());
        System.out.println("Price: " + food.getPrice());
        System.out.println("Description: " + description.getText());
        System.out.println("--------------");
    }

    private static void displayOrderDetails(Order order) {
        Food food = foodService.getByID(order.getFoodID());
        System.out.println("--------------");
        System.out.println("Food: " + food.getName());
        System.out.println("Quantity: " + order.getFoodQuantity());
        System.out.println("Food Price: " + order.getFoodPrice());
        System.out.println("--------------");
    }

    private static void displayConfirmationMenu() {
        MenuUtils.menu(MenuUtils.CONFIRMATION, getCurrentUser().getLanguage());
    }

    private static void displayDepartmentOrderMenu() {
        MenuUtils.menu(MenuUtils.MAKE_ORDER, getCurrentUser().getLanguage());
    }

    private static void displayUserMenu() {
        MenuUtils.menu(MenuUtils.USER_MENU_AFTER_LOG, getCurrentUser().getLanguage());
    }

    private static void displayUserOrderMenu() {
        MenuUtils.menu(MenuUtils.USER_ORDER, getCurrentUser().getLanguage());
    }

    private static void printInvalidSelectionError() {
        System.out.println(MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getCurrentUser().getLanguage()));
    }

    private static User getCurrentUser() {
        return UserController.curUser;
    }
}
