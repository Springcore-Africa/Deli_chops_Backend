package africa.springCore.delichopsbackend.core.portfolio.order.service;

import africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.request.OrderCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.request.ProductOrderCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.response.OrderListingDto;
import africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.response.OrderResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.order.exception.OrderCreationFailedException;
import africa.springCore.delichopsbackend.core.portfolio.order.exception.OrderNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.order.exception.OrderUpdateFailedException;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductCategoryNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductNotFoundException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderResponseDto postAnOrder(Long customerId, OrderCreationRequest orderCreationRequest) throws MapperException, ProductNotFoundException, UserNotFoundException, ProductCategoryNotFoundException, OrderCreationFailedException;

    OrderListingDto getAllOrders(Pageable pageable, String orderStatus);

    OrderResponseDto getOrderById(Long id) throws OrderNotFoundException, MapperException, ProductCategoryNotFoundException, ProductNotFoundException;

    OrderListingDto getCustomerOrders(Long customerId, String orderStatus, Pageable pageable) throws UserNotFoundException, MapperException;

    BigDecimal calculateTotalAmount(List<ProductOrderCreationRequest> productOrders, String orderType) throws ProductCategoryNotFoundException, MapperException, ProductNotFoundException;

    OrderResponseDto updateOrderStatus(Long orderId, String command) throws OrderNotFoundException, ProductCategoryNotFoundException, MapperException, ProductNotFoundException, OrderUpdateFailedException;
}
