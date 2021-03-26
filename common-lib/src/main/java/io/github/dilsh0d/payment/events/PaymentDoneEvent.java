package io.github.dilsh0d.payment.events;

import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
public class PaymentDoneEvent {
    @SagaAssociateId
    private String id;
}
