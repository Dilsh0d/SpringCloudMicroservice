package io.github.dilsh0d.controller;

import io.github.dilsh0d.payment.events.CardPayPaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.kassa.microservice.saga.gateway.SagaGateway;

/**
 * @author Tadjiev Dilshod
 */
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final SagaGateway sagaGateway;

    @PostMapping(value = "/card-pay")
    public @ResponseBody
    String createOrder(@RequestBody CardPayPaymentEvent event) {
        sagaGateway.send(event);
        return event.getId();
    }
}
