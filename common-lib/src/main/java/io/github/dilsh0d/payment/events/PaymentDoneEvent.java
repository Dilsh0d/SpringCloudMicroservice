package io.github.dilsh0d.payment.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
@AllArgsConstructor
public class PaymentDoneEvent {
    @SagaAssociateId
    private String id;
}
