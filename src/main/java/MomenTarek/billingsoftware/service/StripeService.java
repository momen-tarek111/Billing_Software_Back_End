package MomenTarek.billingsoftware.service;

import MomenTarek.billingsoftware.io.StripePaymentResponse;


public interface StripeService {
    StripePaymentResponse createPaymentIntent(Double amount, String currency) throws Exception;
}
