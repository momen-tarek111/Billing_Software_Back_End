package MomenTarek.billingsoftware.io;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeVerificationRequest {
    private String paymentIntentId;
    private String orderId;
}
