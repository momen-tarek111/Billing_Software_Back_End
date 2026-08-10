package MomenTarek.billingsoftware.io;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String currency;
    private Double amount;
}
