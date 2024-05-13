package com.pdp.controller;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.java.console.ListUtils;
import com.pdp.java.console.NotificationHandler;
import com.pdp.java.console.Scan;
import com.pdp.utils.MenuUtils;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.address.Address;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.description.Description;
import com.pdp.web.service.address.AddressService;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.description.DescriptionService;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * Date: 11/May/2024  18:44
 **/
public class DelivererController {

    private static final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private static final BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();
    private static final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private static final AddressService addressService = ThreadSafeBeansContainer.addressServiceThreadLocal.get();
    private static final DescriptionService descriptionService = ThreadSafeBeansContainer.descriptionServiceThreadLocal.get();

    public static void deliverer() {
        while (true) {
            MenuUtils.menu(MenuUtils.DELIVERER_MENU, LoginController.getCurUserLanguage());
            switch (Scan.scanInt()) {
                case 1 -> orderAcceptance();
                case 2 -> activeOrder();
                case 0 -> {
                    LoginController.userSignInSignUp(false);
                    UserController.handlePostLogin();
                }
                default -> printInvalidSelectionError();
            }
        }
    }

    private static void orderAcceptance() {
        List<CustomerOrder> customerOrdersByDeliverer = customerOrderService.getOrdersInProcessByDeliverer(UserController.curUser.getId());
        if (ListUtils.checkDataForNotNull(customerOrdersByDeliverer)) {
            NotificationHandler.notifyAction("You have an active order", "", false);
        } else {
            List<CustomerOrder> customerOrders = customerOrderService.getPendingOrdersForDeliverer();
            NotificationHandler.checkData(customerOrders);
            if (ListUtils.checkDataForNotNull(customerOrders)) {
                displayOrders(customerOrders);
                int index = Scan.scanInt() - 1;
                if (index < 0 || index >= customerOrders.size()) {
                    printInvalidSelectionError();
                    return;
                }
                CustomerOrder customerOrder = customerOrders.get(index);
                customerOrder.setDeliverID(UserController.curUser.getId());
                customerOrder.setOrderStatus(OrderStatus.YOUR_ORDER_RECEIVED);
                customerOrderService.update(customerOrder);
            }
        }
    }

    private static void activeOrder() {
        List<CustomerOrder> customerOrders = customerOrderService.getOrdersInProcessByDeliverer(UserController.curUser.getId());
        NotificationHandler.checkData(customerOrders);
        if (ListUtils.checkDataForNotNull(customerOrders)) {
            displayOrders(customerOrders);
            int index = Scan.scanInt() - 1;
            if (index < 0 || index >= customerOrders.size()) {
                printInvalidSelectionError();
                return;
            }
            CustomerOrder customerOrder = customerOrders.get(index);
            handleOrderStatus(customerOrder);
        }
    }

    private static void handleOrderStatus(CustomerOrder customerOrder) {
        OrderStatus orderStatus = customerOrder.getOrderStatus();
        switch (orderStatus) {
            case YOUR_ORDER_RECEIVED -> handleReceivedOrder(customerOrder);
            case IN_TRANSIT -> handleInTransitOrder(customerOrder);
            default -> printInvalidSelectionError();
        }
    }

    private static void handleReceivedOrder(CustomerOrder customerOrder) {

        MenuUtils.menu(MenuUtils.DELIVERER_GENERAL_MENU, LoginController.getCurUserLanguage());
        switch (Scan.scanInt()) {
            case 1 -> confirmOrderReceipt(customerOrder);
            case 2 -> cancelDelivery(customerOrder);
            case 0 -> {}
            default -> printInvalidSelectionError();
        }

    }

    private static void handleInTransitOrder(CustomerOrder customerOrder) {

        MenuUtils.menu(MenuUtils.DELIVERER_GENERAL_FOLLOWING, LoginController.getCurUserLanguage());
        switch (Scan.scanInt()) {
            case 1 -> confirmDelivery(customerOrder);
            case 2 -> cancelDelivery(customerOrder);
            case 0 -> {}
            default -> printInvalidSelectionError();
        }

    }

    private static void cancelDelivery(CustomerOrder customerOrder) {
        updateOrderStatus(customerOrder, OrderStatus.FAILED_DELIVERY);
        MenuUtils.menu("menu.deliver.reason", LoginController.getCurUserLanguage());
        String text = Scan.scanStr();
        Description description = new Description("Canceled Order", text);
        descriptionService.add(description);
        customerOrder.setDescriptionID(description.getId());
        customerOrderService.update(customerOrder);
    }

    private static void confirmDelivery(CustomerOrder customerOrder) {
        updateOrderStatus(customerOrder, OrderStatus.DELIVERED);
    }

    private static void confirmOrderReceipt(CustomerOrder customerOrder) {
        updateOrderStatus(customerOrder, OrderStatus.IN_TRANSIT);
    }


    private static void displayOrders(List<CustomerOrder> customerOrders) {
        int i = 1;
        for (CustomerOrder customerOrder : customerOrders) {
            Branch branch = branchService.getByID(customerOrder.getBranchID());
            Brand brand = brandService.getByID(branch.getBrandID());
            Address addressById = addressService.getByID(customerOrder.getAddressID());
            String address = String.format("%s %s %d,%d", addressById.getCity(), addressById.getStreet(), addressById.getHouseNumber(), addressById.getApartmentNumber());
            System.out.printf("""
                    [%d] - Order Number
                    Address : %s
                    Brand : %s
                    %s:%s
                    """, i++, address, brand.getDisplayName(), MessageSourceUtils.getLocalizedMessage("info.totalAmount", UserController.curUser.getLanguage()), customerOrder.getOrderPrice());
        }
    }

    private static void updateOrderStatus(CustomerOrder customerOrder, OrderStatus status) {
        customerOrder.setOrderStatus(status);
        customerOrderService.update(customerOrder);
    }

    private static void printInvalidSelectionError() {
        MenuUtils.menu("error.invalidCredentials", LoginController.getCurUserLanguage());
    }
}
