package io.github.dilsh0d.order.events;

import io.github.dilsh0d.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
public class OrderPaymentEntityCreatedEvent {

    @SagaAssociateId
    private String id;

    private String paymentId;
    private PaymentType paymentType;

}
