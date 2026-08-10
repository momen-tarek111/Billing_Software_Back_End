package MomenTarek.billingsoftware.io;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {
    private String stripePaymentIntentId;
    private String stripeClientSecret;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
