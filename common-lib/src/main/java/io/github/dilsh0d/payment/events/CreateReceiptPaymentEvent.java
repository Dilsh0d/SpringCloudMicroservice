package io.github.dilsh0d.payment.events;

import io.github.dilsh0d.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;

import java.math.BigDecimal;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
public class CreateReceiptPaymentEvent {

    @SagaAssociateId
    private String id;
    private String orderId;
    private PaymentType paymentType;
    private BigDecimal amount;
}
