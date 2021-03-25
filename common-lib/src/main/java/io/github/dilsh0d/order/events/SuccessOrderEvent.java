package io.github.dilsh0d.order.events;

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
public class SuccessOrderEvent {

    @SagaAssociateId
    private String id;
}
