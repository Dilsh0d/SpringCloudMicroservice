package io.github.dilsh0d.saga;

import io.github.dilsh0d.component.PaymentPushNotification;
import io.github.dilsh0d.enums.PaymentType;
import io.github.dilsh0d.order.events.OrderPaymentEntityCreatedEvent;
import io.github.dilsh0d.order.events.SuccessOrderEvent;
import io.github.dilsh0d.payment.events.CardPayPaymentEvent;
import io.github.dilsh0d.payment.events.CreateReceiptPaymentEvent;
import io.github.dilsh0d.payment.events.PaymentDoneEvent;
import io.github.dilsh0d.payment.events.TryAgainReceiptPaymentEvent;
import io.github.dilsh0d.service.PaymentService;
import io.github.dilsh0d.service.payment.PayPalStrategy;
import io.github.dilsh0d.service.payment.StripeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import uz.kassa.microservice.saga.annotation.SagaOrchestEnd;
import uz.kassa.microservice.saga.annotation.SagaOrchestEventHandler;
import uz.kassa.microservice.saga.annotation.SagaOrchestStart;
import uz.kassa.microservice.saga.annotation.SagaOrchestration;
import uz.kassa.microservice.saga.gateway.SagaGateway;

import java.math.BigDecimal;


/**
 * @author Tadjiev Dilshod
 */
@SagaOrchestration
public class PaymentSaga {

    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private String cardNumber;
    private String cardExpired;
    private String cardCvv;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpired() {
        return cardExpired;
    }

    public void setCardExpired(String cardExpired) {
        this.cardExpired = cardExpired;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    @Autowired
    private transient SagaGateway sagaGateway;

    @Autowired
    private transient PaymentService paymentService;

    @Autowired
    private transient PaymentPushNotification paymentPushNotification;

    @SagaOrchestStart
    @SagaOrchestEventHandler
    public void handler(CreateReceiptPaymentEvent event) {
        this.paymentId = event.getId();
        this.orderId = event.getOrderId();
        this.amount = event.getAmount();
        this.paymentType = event.getPaymentType();

        paymentService.createPayment(event);

        OrderPaymentEntityCreatedEvent orderPaymentEntityCreatedEvent = new OrderPaymentEntityCreatedEvent();
        orderPaymentEntityCreatedEvent.setId(orderId);
        orderPaymentEntityCreatedEvent.setPaymentId(paymentId);
        orderPaymentEntityCreatedEvent.setPaymentType(paymentType);

        sagaGateway.send(orderPaymentEntityCreatedEvent);
    }

    @SagaOrchestEventHandler
    public void handler(TryAgainReceiptPaymentEvent event) {
        this.paymentType = event.getPaymentType();

        OrderPaymentEntityCreatedEvent orderPaymentEntityCreatedEvent = new OrderPaymentEntityCreatedEvent();
        orderPaymentEntityCreatedEvent.setId(orderId);
        orderPaymentEntityCreatedEvent.setPaymentId(paymentId);
        orderPaymentEntityCreatedEvent.setPaymentType(paymentType);

        sagaGateway.send(orderPaymentEntityCreatedEvent);

    }

    @SagaOrchestEventHandler
    public void handler(CardPayPaymentEvent event) {
        this.cardNumber = event.getCardNumber();
        this.cardExpired = event.getCardExpired();
        this.cardCvv = event.getCardCvv();

        if(paymentType ==null || paymentType == PaymentType.None){
            paymentPushNotification.sendMessageClient(paymentId,orderId,"Please select payment type");
        } else if (PaymentType.PayPal.equals(paymentType)){
            paymentService.pay(event, new PayPalStrategy(cardNumber, cardExpired, cardCvv));
        } else {
            paymentService.pay(event, new StripeStrategy(cardNumber, cardExpired, cardCvv));
        }

        PaymentDoneEvent paymentDoneEvent = new PaymentDoneEvent();
        paymentDoneEvent.setId(paymentId);

        sagaGateway.send(paymentDoneEvent);

    }

    @SagaOrchestEnd
    @SagaOrchestEventHandler
    public void handler(PaymentDoneEvent event) {
        SuccessOrderEvent successOrderEvent = new SuccessOrderEvent();
        successOrderEvent.setId(orderId);
        sagaGateway.send(successOrderEvent);

        System.out.println("-------------------------------PAYMENT SAGA DONE-----------------------------");
    }
}
