package MomenTarek.billingsoftware.controller;

import MomenTarek.billingsoftware.io.*;
import MomenTarek.billingsoftware.service.OrderService;
import MomenTarek.billingsoftware.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final StripeService stripeService;
    private final OrderService orderService;

    @PostMapping("/create-payment-intent")
    @ResponseStatus(HttpStatus.CREATED)
    public StripePaymentResponse createPaymentIntent(
            @RequestBody PaymentRequest request) throws Exception {

        return stripeService.createPaymentIntent(
                request.getAmount(),
                request.getCurrency()
        );
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(
            @RequestBody StripeVerificationRequest request) {

        return orderService.verifyStripePayment(request);
    }
}
