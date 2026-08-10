package MomenTarek.billingsoftware.service.impl;

import MomenTarek.billingsoftware.entity.OrderEntity;
import MomenTarek.billingsoftware.entity.OrderItemEntity;
import MomenTarek.billingsoftware.io.*;
import MomenTarek.billingsoftware.repository.OrderEntityRepository;
import MomenTarek.billingsoftware.service.OrderService;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity newOrder=convertToOrderEntity(request);
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setPaymentStatus(newOrder.getPaymentMethod()== PaymentMethod.CASH?
                PaymentStatus.COMPLETED:PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);
        List<OrderItemEntity> orderItems=request.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
        newOrder.setItems(orderItems);
        newOrder=orderEntityRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .createdAt(newOrder.getCreatedAt())
                .tax(newOrder.getTax())
                .subtotal(newOrder.getSubtotal())
                .grandTotal(newOrder.getGrandTotal())
                .paymentDetails(newOrder.getPaymentDetails())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream().map(this::convertToOrderItemResponse).collect(Collectors.toList()))
                .build();
    }

    private OrderResponse.OrderItemsResponse convertToOrderItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemsResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemsRequest orderItemsRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemsRequest.getItemId())
                .name(orderItemsRequest.getName())
                .price(orderItemsRequest.getPrice())
                .quantity(orderItemsRequest.getQuantity())
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(request.getSubtotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
        OrderEntity existingOrder=orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        orderEntityRepository.delete(existingOrder);
    }
    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyStripePayment(StripeVerificationRequest request) {

        OrderEntity existingOrder = orderEntityRepository
                .findByOrderId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            PaymentIntent paymentIntent =
                    PaymentIntent.retrieve(request.getPaymentIntentId());

            if (!"succeeded".equals(paymentIntent.getStatus())) {
                throw new RuntimeException("Payment not successful");
            }

            PaymentDetails paymentDetails = PaymentDetails.builder()
                    .stripePaymentIntentId(paymentIntent.getId())
                    .stripeClientSecret(paymentIntent.getClientSecret())
                    .paymentStatus(PaymentStatus.COMPLETED)
                    .build();
            existingOrder.setPaymentDetails(paymentDetails);
            existingOrder = orderEntityRepository.save(existingOrder);
            return convertToResponse(existingOrder);
        } catch (Exception e) {
            throw new RuntimeException("Payment verification failed");
        }
    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepository.sunSaleByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepository.findRecentOrders(PageRequest.of(0,5)).stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}
