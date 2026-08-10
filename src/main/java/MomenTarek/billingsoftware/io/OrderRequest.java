package MomenTarek.billingsoftware.io;

import MomenTarek.billingsoftware.entity.OrderItemEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private String customerName;
    private String phoneNumber;
    private List<OrderItemsRequest> cartItems;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;
    private String paymentMethod;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class OrderItemsRequest{
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;
    }

}
