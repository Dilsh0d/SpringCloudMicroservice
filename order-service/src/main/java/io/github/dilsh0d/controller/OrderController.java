package io.github.dilsh0d.controller;

import io.github.dilsh0d.order.events.ChooseOrderPaymentTypeEvent;
import io.github.dilsh0d.order.events.CreateOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.kassa.microservice.saga.gateway.SagaGateway;

import java.util.UUID;

/**
 * @author Tadjiev Dilshod
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final SagaGateway sagaGateway;

    @PostMapping(value = "/create")
    public @ResponseBody String createOrder(@RequestBody CreateOrderEvent event) {
        String orderId = UUID.randomUUID().toString();
        event.setId(orderId);

        sagaGateway.send(event);
        return event.getId();
    }

    @PostMapping(value = "/choose-payment-type")
    public @ResponseBody String createOrder(@RequestBody ChooseOrderPaymentTypeEvent event) {
        sagaGateway.send(event);
        return event.getId();
    }
}
