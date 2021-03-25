package io.github.dilsh0d.payment.events;

import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
public class CardPayPaymentEvent {

    @SagaAssociateId
    private String id;

    private String cardNumber;
    private String cardExpired;
    private String cardCvv;
}
