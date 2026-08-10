package MomenTarek.billingsoftware.service.impl;

import MomenTarek.billingsoftware.io.StripePaymentResponse;
import MomenTarek.billingsoftware.service.StripeService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    @Override
    public StripePaymentResponse createPaymentIntent(Double amount, String currency) throws Exception {

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (amount * 100))
                        .setCurrency(currency)

                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return StripePaymentResponse.builder()
                .paymentIntentId(paymentIntent.getId())
                .clientSecret(paymentIntent.getClientSecret())
                .status(paymentIntent.getStatus())
                .amount(paymentIntent.getAmount())
                .currency(paymentIntent.getCurrency())
                .created_at(new Date(paymentIntent.getCreated() * 1000))
                .build();
    }
}
