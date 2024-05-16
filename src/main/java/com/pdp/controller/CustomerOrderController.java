package com.pdp.controller;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.CustomOrderDTO;
import com.pdp.java.console.ListUtils;
import com.pdp.java.console.NotificationHandler;
import com.pdp.java.console.Scan;
import com.pdp.utils.front.MenuUtils;
import com.pdp.utils.front.Utils;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.enums.PaymentType;
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
import java.util.*;

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
                    UserController.curUser = null;
                    LoginController.userSignInSignUp(false);
                    UserController.handlePostLogin();
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
        if (Objects.isNull(notConfirmedOrder)) {
            NotificationHandler.checkData(Collections.EMPTY_LIST);
            System.out.println(MessageSourceUtils.getLocalizedMessage("error.cartEmpty", getCurrentUser().getLanguage()));
            return;
        }
        List<Order> orders = orderService.getOrdersByCustomerID(notConfirmedOrder.getId());
        orders.forEach(CustomerOrderController::displayOrderDetails);
        displayCartMenu();
        switch (Scan.scanInt()) {
            case 1 -> activateMyOrder(notConfirmedOrder);
            case 2 -> makeEmpty(notConfirmedOrder);
        }
    }

    private static void activateMyOrder(CustomerOrder customerOrder) {
        customerOrder.setOrderStatus(OrderStatus.LOOKING_FOR_A_DELIVERER);
        Address address = confirmAddress();
        PaymentType paymentType = confirmPaymentType();
        customerOrder.setAddressID(address.getId());
        customerOrder.setPaymentType(paymentType);
        customerOrder.setUserID(getCurrentUser().getId());
        customerOrder.setOrderPrice(priceOfOrder(customerOrder));
        customerOrderService.update(customerOrder);
        System.out.println(MessageSourceUtils.getLocalizedMessage("success.orderPlaced", getCurrentUser().getLanguage()));
    }

    private static BigDecimal priceOfOrder(CustomerOrder customerOrder) {
        return orderService.getOrderPrice(customerOrder.getId());
    }

    private static void makeEmpty(CustomerOrder customerOrder) {
        displayConfirmationMenu();
        int i = Scan.scanInt();
        boolean success = i == 1;
        if (success) {
            List<Order> orders = orderService.getOrdersByCustomerID(customerOrder.getId());
            orders.forEach(order -> orderService.remove(order.getId()));
            boolean removed = customerOrderService.remove(customerOrder.getId());
            NotificationHandler.notifyAction("Cart", "cleaned", removed);
        }

    }

    private static PaymentType confirmPaymentType() {
        List<PaymentType> paymentTypes = List.of(PaymentType.values());
        ListUtils.displayList(paymentTypes);
        return Utils.getElementByIndex(paymentTypes, Scan.scanInt());
    }

    private static Address confirmAddress() {
        String city = Scan.scanStr("Enter city");
        String street = Scan.scanStr("Enter street");
        int apartmentNumber = Scan.scanInt("Enter apartment number");
        int houseNumber = Scan.scanInt("Enter house number");
        Address address = new Address(city, street, apartmentNumber, houseNumber);
        addressService.add(address);
        return address;
    }

    private static void registerAsCourier() {
        System.out.println(MessageSourceUtils.getLocalizedMessage("main.menu.login", getCurrentUser().getLanguage()));
        String phoneNumber = Scan.scanStr("Enter phone number");
        Deliverer deliverer = new Deliverer(getCurrentUser().getFullname(), phoneNumber);
        Transport transport = Transport.builder()
                .deliverID(deliverer.getId())
                .name(Scan.scanStr("Enter transport name"))
                .registeredNumber(Scan.scanStr("Enter registered number"))
                .build();
        User currentUser = getCurrentUser();
        currentUser.setRole(Role.DELIVERER);
        boolean updated = userService.update(currentUser);
        boolean isDelivererSuccesfull = delivererService.add(deliverer);
        boolean isTransportSuccesfull = transportService.add(transport);
        UserController.curUser = currentUser;
        NotificationHandler.notifyAction("Deliverer", "added", updated && isDelivererSuccesfull && isTransportSuccesfull);
        LoginController.userSignInSignUp(false);
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
            if (Objects.isNull(branch)) return;
            CustomerOrder customerOrder = customerOrderService.getOrCreate(getCurrentUser().getId(), branch.getId());
            if (Objects.isNull(customerOrder)) return;
            CustomOrderDTO dto = new CustomOrderDTO(customerOrder.getUserID(), customerOrder.getBranchID());
            Order order = new Order(food.getId(), orderPrice, quantity, customerOrder.getId());
            Order serviceOrCreate = orderService.getOrCreate(dto, order);
            if (Objects.isNull(serviceOrCreate)) return;
            NotificationHandler.notifyAction("Order", "added", true);
            System.out.println(MessageSourceUtils.getLocalizedMessage("success.itemAddedToCart", getCurrentUser().getLanguage()));
        }
    }

    private static Brand selectBrand() {
        List<Brand> brands = brandService.getAll();
        NotificationHandler.checkData(brands);
        if (ListUtils.checkDataForNotNull(brands)) {
            ListUtils.displayListByName(brands);
            Brand brand = Utils.getElementByIndex(brands, Scan.scanInt());
            if (Objects.isNull(brand)) return null;
            displayBrandDescription(brand);
            return brand;
        }
        return null;
    }

    private static Category selectCategory(UUID brandID) {
        Set<Category> categories = categoryService.getBrandCategories(brandID);
        NotificationHandler.checkData(List.of(categories));
        if (ListUtils.checkDataForNotNull(List.of(categories))) {
            int i = 0;
            for (Category category : categories) {
                System.out.println((i + 1) + " " + category.getName());
                i += 1;
            }
            return Utils.getElementByIndexForSet(categories, Scan.scanInt());
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
        NotificationHandler.checkData(ordersInProcessByUser);
        displayOrders(ordersInProcessByUser);
    }

    private static void showArchive() {
        List<CustomerOrder> customerOrdersArchive = customerOrderService.getArchive(getCurrentUser().getId());
        NotificationHandler.checkData(customerOrdersArchive);
        displayOrders(customerOrdersArchive);
    }

    private static void displayOrders(List<CustomerOrder> orders) {
        for (CustomerOrder customerOrder : orders) {
            List<Order> orderList = orderService.getOrdersByCustomerID(customerOrder.getId());
            if (!orderList.isEmpty()) {
                Address address = addressService.getByID(customerOrder.getAddressID());
                System.out.println("------------------------------");
                System.out.println("Address:");
                System.out.println("City: " + address.getCity());
                System.out.println("Street: " + address.getStreet());
                System.out.println("Order Status: " + customerOrder.getOrderStatus());
                orderList.forEach(CustomerOrderController::displayOrderDetails);
                System.out.println("Description: " + descriptionService.getByID(customerOrder.getDescriptionID()).getText());
                System.out.println("Finally Price: 0");
                System.out.println("------------------------------");
            }
        }
    }

    private static void displayBrandDescription(Brand brand) {
        Description description = descriptionService.getByID(brand.getDescriptionID());
        System.out.println("------------------------------");
        System.out.println(brand.getName());
        System.out.println(description.getText());
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

    private static void displayCartMenu() {
        MenuUtils.menu(MenuUtils.CART_OPERATION, getCurrentUser().getLanguage());
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
