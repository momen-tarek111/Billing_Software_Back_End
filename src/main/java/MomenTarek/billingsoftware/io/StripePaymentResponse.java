package MomenTarek.billingsoftware.io;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripePaymentResponse {
    private String paymentIntentId;
    private String clientSecret;
    private String status;
    private Long amount;
    private String currency;
    private Date created_at;
}
