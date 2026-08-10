package MomenTarek.billingsoftware.io;

import MomenTarek.billingsoftware.entity.OrderItemEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String customerName;
    private String phoneNumber;
    private List<OrderItemsResponse> items;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private PaymentDetails paymentDetails;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class OrderItemsResponse{
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;
    }

}
