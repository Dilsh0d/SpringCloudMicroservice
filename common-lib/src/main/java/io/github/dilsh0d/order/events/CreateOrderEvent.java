package io.github.dilsh0d.order.events;

import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

import java.util.List;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
public class CreateOrderEvent {

    @SagaAssociateId
    private String id;

    private List<Integer> items;

}
