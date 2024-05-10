package com.pdp.backend.config;

import com.pdp.backend.web.service.address.AddressService;
import com.pdp.backend.web.service.address.AddressServiceImp;
import com.pdp.backend.web.service.banned.BannedService;
import com.pdp.backend.web.service.banned.BannedServiceImp;
import com.pdp.backend.web.service.branch.BranchService;
import com.pdp.backend.web.service.branch.BranchServiceImp;
import com.pdp.backend.web.service.brand.BrandService;
import com.pdp.backend.web.service.brand.BrandServiceImp;
import com.pdp.backend.web.service.category.CategoryService;
import com.pdp.backend.web.service.category.CategoryServiceImp;
import com.pdp.backend.web.service.comment.CommentService;
import com.pdp.backend.web.service.comment.CommentServiceImp;
import com.pdp.backend.web.service.customerOrder.CustomerOrderService;
import com.pdp.backend.web.service.customerOrder.CustomerOrderServiceImp;
import com.pdp.backend.web.service.deliverer.DelivererService;
import com.pdp.backend.web.service.deliverer.DelivererServiceImp;
import com.pdp.backend.web.service.description.DescriptionService;
import com.pdp.backend.web.service.description.DescriptionServiceImp;
import com.pdp.backend.web.service.discount.DiscountService;
import com.pdp.backend.web.service.discount.DiscountServiceImp;
import com.pdp.backend.web.service.food.FoodService;
import com.pdp.backend.web.service.food.FoodServiceImp;
import com.pdp.backend.web.service.foodBrandMapping.FoodBrandMappingService;
import com.pdp.backend.web.service.foodBrandMapping.FoodBrandMappingServiceImp;
import com.pdp.backend.web.service.order.OrderService;
import com.pdp.backend.web.service.order.OrderServiceImp;
import com.pdp.backend.web.service.transport.TransportService;
import com.pdp.backend.web.service.transport.TransportServiceImp;
import com.pdp.backend.web.service.user.UserService;
import com.pdp.backend.web.service.user.UserServiceImp;

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
 * @since 10/May/2024 10:48
 */
public class ThreadSafeBeansContainer {

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
}
