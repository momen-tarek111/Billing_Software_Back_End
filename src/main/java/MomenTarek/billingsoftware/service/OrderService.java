package MomenTarek.billingsoftware.service;

import MomenTarek.billingsoftware.io.OrderRequest;
import MomenTarek.billingsoftware.io.OrderResponse;
import MomenTarek.billingsoftware.io.StripeVerificationRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    void deleteOrder(String orderId);
    List<OrderResponse> getLatestOrders();

    OrderResponse verifyStripePayment(StripeVerificationRequest request);

    Double sumSalesByDate(LocalDate date);
    Long countByOrderDate(LocalDate date);
    List<OrderResponse> findRecentOrders();
}
