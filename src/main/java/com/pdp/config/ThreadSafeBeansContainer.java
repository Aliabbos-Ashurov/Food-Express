package com.pdp.config;

import com.pdp.telegram.handler.CallbackHandler;
import com.pdp.telegram.handler.Handler;
import com.pdp.telegram.handler.MessageHandler;
import com.pdp.telegram.processor.callback.deliverer.ActivateOrderCallbackProcessor;
import com.pdp.telegram.processor.callback.deliverer.DeliveryMenuCallbackProcessor;
import com.pdp.telegram.processor.callback.user.*;
import com.pdp.telegram.processor.message.deliverer.ActivateOrderMessageProcessor;
import com.pdp.telegram.processor.message.deliverer.DeliveryMenuMessageProcessor;
import com.pdp.telegram.processor.message.user.*;
import com.pdp.telegram.service.customerOrderGeoPiont.CustomerOrderGeoPointService;
import com.pdp.telegram.service.customerOrderGeoPiont.CustomerOrderGeoPointServiceImp;
import com.pdp.telegram.service.telegramDeliverer.TelegramDelivererService;
import com.pdp.telegram.service.telegramDeliverer.TelegramDelivererServiceImp;
import com.pdp.telegram.service.telegramTransport.TelegramTransportService;
import com.pdp.telegram.service.telegramTransport.TelegramTransportServiceImp;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.service.telegramUser.TelegramUserServiceImp;
import com.pdp.web.service.address.AddressService;
import com.pdp.web.service.address.AddressServiceImp;
import com.pdp.web.service.banned.BannedService;
import com.pdp.web.service.banned.BannedServiceImp;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.branch.BranchServiceImp;
import com.pdp.web.service.branchLocation.BranchLocationService;
import com.pdp.web.service.branchLocation.BranchLocationServiceImp;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.brand.BrandServiceImp;
import com.pdp.web.service.category.CategoryService;
import com.pdp.web.service.category.CategoryServiceImp;
import com.pdp.web.service.comment.CommentService;
import com.pdp.web.service.comment.CommentServiceImp;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.customerOrder.CustomerOrderServiceImp;
import com.pdp.web.service.deliverer.DelivererService;
import com.pdp.web.service.deliverer.DelivererServiceImp;
import com.pdp.web.service.description.DescriptionService;
import com.pdp.web.service.description.DescriptionServiceImp;
import com.pdp.web.service.discount.DiscountService;
import com.pdp.web.service.discount.DiscountServiceImp;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.food.FoodServiceImp;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingServiceImp;
import com.pdp.web.service.login.LoginService;
import com.pdp.web.service.login.LoginServiceImpl;
import com.pdp.web.service.order.OrderService;
import com.pdp.web.service.order.OrderServiceImp;
import com.pdp.web.service.transport.TransportService;
import com.pdp.web.service.transport.TransportServiceImp;
import com.pdp.web.service.user.UserService;
import com.pdp.web.service.user.UserServiceImp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Central configuration class that provides thread-local access to service layer beans.
 * This ensures that the service instances are thread-safe when accessed concurrently.
 *
 * @author Aliabbos Ashurov
 * @see AddressService
 * @see BannedService
 * @see BranchService
 * @see BrandService
 * @see CategoryService
 * @see CommentService
 * @see CustomerOrderService
 * @see DelivererService
 * @see DescriptionService
 * @see DiscountService
 * @see FoodService
 * @see FoodBrandMappingService
 * @see OrderService
 * @see TransportService
 * @see UserService
 * @see BranchLocationService
 * @since 10/May/2024 10:48
 */
public class ThreadSafeBeansContainer {
    public static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // WEB
    public static final ThreadLocal<AddressService> addressServiceThreadLocal = ThreadLocal.withInitial(AddressServiceImp::getInstance);
    public static final ThreadLocal<BannedService> bannedServiceThreadLocal = ThreadLocal.withInitial(BannedServiceImp::getInstance);
    public static final ThreadLocal<BranchService> branchServiceThreadLocal = ThreadLocal.withInitial(BranchServiceImp::getInstance);
    public static final ThreadLocal<BrandService> brandServiceThreadLocal = ThreadLocal.withInitial(BrandServiceImp::getInstance);
    public static final ThreadLocal<CategoryService> categoryServiceThreadLocal = ThreadLocal.withInitial(CategoryServiceImp::getInstance);
    public static final ThreadLocal<CommentService> commentServiceThreadLocal = ThreadLocal.withInitial(CommentServiceImp::getInstance);
    public static final ThreadLocal<CustomerOrderService> customerOrderServiceThreadLocal = ThreadLocal.withInitial(CustomerOrderServiceImp::getInstance);
    public static final ThreadLocal<DelivererService> delivererServiceThreadLocal = ThreadLocal.withInitial(DelivererServiceImp::getInstance);
    public static final ThreadLocal<DescriptionService> descriptionServiceThreadLocal = ThreadLocal.withInitial(DescriptionServiceImp::getInstance);
    public static final ThreadLocal<DiscountService> discountServiceThreadLocal = ThreadLocal.withInitial(DiscountServiceImp::getInstance);
    public static final ThreadLocal<FoodService> foodServiceThreadLocal = ThreadLocal.withInitial(FoodServiceImp::getInstance);
    public static final ThreadLocal<FoodBrandMappingService> foodBrandMappingServiceThreadLocal = ThreadLocal.withInitial(FoodBrandMappingServiceImp::getInstance);
    public static final ThreadLocal<OrderService> orderServiceThreadLocal = ThreadLocal.withInitial(OrderServiceImp::getInstance);
    public static final ThreadLocal<TransportService> transportServiceThreadLocal = ThreadLocal.withInitial(TransportServiceImp::getInstance);
    public static final ThreadLocal<UserService> userServiceThreadLocal = ThreadLocal.withInitial(UserServiceImp::getInstance);
    public static final ThreadLocal<BranchLocationService> branchLocationServiceThreadLocal = ThreadLocal.withInitial(BranchLocationServiceImp::getInstance);
    public static final ThreadLocal<LoginService> loginServiceThreadLocal = ThreadLocal.withInitial(LoginServiceImpl::getInstance);
    //Handler
    public static final ThreadLocal<Handler> messageHandlerThreadLocal = ThreadLocal.withInitial(MessageHandler::new);
    public static final ThreadLocal<Handler> callbackHandlerThreadLocal = ThreadLocal.withInitial(CallbackHandler::new);
    //Telegram Service
    public static final ThreadLocal<CustomerOrderGeoPointService> geoPointServiceThreadLocal = ThreadLocal.withInitial(CustomerOrderGeoPointServiceImp::getInstance);
    public static final ThreadLocal<TelegramDelivererService> telegramDelivererServiceThreadLocal = ThreadLocal.withInitial(TelegramDelivererServiceImp::getInstance);
    public static final ThreadLocal<TelegramTransportService> telegramTransportServiceThreadLocal = ThreadLocal.withInitial(TelegramTransportServiceImp::getInstance);
    public static final ThreadLocal<TelegramUserService> telegramUserServiceThreadLocal = ThreadLocal.withInitial(TelegramUserServiceImp::getInstance);
    ///Processor
    //Callback
    public static final ThreadLocal<ActivateOrderCallbackProcessor> activateOrderCallbackProcessor = ThreadLocal.withInitial(ActivateOrderCallbackProcessor::new);
    public static final ThreadLocal<DeliveryMenuCallbackProcessor> deliveryMenuCallbackProcessor = ThreadLocal.withInitial(DeliveryMenuCallbackProcessor::new);
    public static final ThreadLocal<ConfirmationCallbackProcessor> confirmationCallbackProcessor = ThreadLocal.withInitial(ConfirmationCallbackProcessor::new);
    public static final ThreadLocal<ConfirmOrderCallbackProcessor> confirmOrderCallbackProcessor = ThreadLocal.withInitial(ConfirmOrderCallbackProcessor::new);
    public static final ThreadLocal<CourierRegistrationCallbackProcessor> courierRegistrationCallbackProcessor = ThreadLocal.withInitial(CourierRegistrationCallbackProcessor::new);
    public static final ThreadLocal<MyOrderCallbackProcessor> myOrderCallbackProcessor = ThreadLocal.withInitial(MyOrderCallbackProcessor::new);
    public static final ThreadLocal<OrderManagementCallbackProcessor> orderManagementCallbackProcessor = ThreadLocal.withInitial(OrderManagementCallbackProcessor::new);
    public static final ThreadLocal<OrderPlacementCallbackProcessor> orderPlacementCallbackProcessor = ThreadLocal.withInitial(OrderPlacementCallbackProcessor::new);
    public static final ThreadLocal<UserMenuOptionCallbackProcessor> userMenuOptionCallbackProcessor = ThreadLocal.withInitial(UserMenuOptionCallbackProcessor::new);
    public static final ThreadLocal<UserViewCallbackProcessor> userViewCallbackProcessor = ThreadLocal.withInitial(UserViewCallbackProcessor::new);
    //Message
    public static final ThreadLocal<ActivateOrderMessageProcessor> activateOrderMessageProcessor = ThreadLocal.withInitial(ActivateOrderMessageProcessor::new);
    public static final ThreadLocal<DeliveryMenuMessageProcessor> deliveryMenuMessageProcessor = ThreadLocal.withInitial(DeliveryMenuMessageProcessor::new);
    public static final ThreadLocal<DefaultMessageProcessor> defaultMessageProcessor = ThreadLocal.withInitial(DefaultMessageProcessor::new);
    public static final ThreadLocal<ConfirmationMessageProcessor> confirmationMessageProcessor = ThreadLocal.withInitial(ConfirmationMessageProcessor::new);
    public static final ThreadLocal<ConfirmOrderMessageProcessor> confirmOrderMessageProcessor = ThreadLocal.withInitial(ConfirmOrderMessageProcessor::new);
    public static final ThreadLocal<CourierRegistrationMessageProcessor> courierRegistrationMessageProcessor = ThreadLocal.withInitial(CourierRegistrationMessageProcessor::new);
    public static final ThreadLocal<MyOrderMessageProcessor> myOrderMessageProcessor = ThreadLocal.withInitial(MyOrderMessageProcessor::new);
    public static final ThreadLocal<OrderManagementMessageProcessor> orderManagementMessageProcessor = ThreadLocal.withInitial(OrderManagementMessageProcessor::new);
    public static final ThreadLocal<OrderPlacementMessageProcessor> orderPlacementMessageProcessor = ThreadLocal.withInitial(OrderPlacementMessageProcessor::new);
    public static final ThreadLocal<UserMenuOptionMessageProcessor> userMenuOptionMessageProcessor = ThreadLocal.withInitial(UserMenuOptionMessageProcessor::new);
    public static final ThreadLocal<UserViewMessageProcessor> userViewMessageProcessor = ThreadLocal.withInitial(UserViewMessageProcessor::new);
}
