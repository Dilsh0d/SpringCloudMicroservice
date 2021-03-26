package io.github.dilsh0d.saga;

import io.github.dilsh0d.component.OrderPushNotification;
import io.github.dilsh0d.enums.PaymentType;
import io.github.dilsh0d.order.events.*;
import io.github.dilsh0d.payment.events.CreateReceiptPaymentEvent;
import io.github.dilsh0d.payment.events.RollbackPaymentEvent;
import io.github.dilsh0d.payment.events.TryAgainReceiptPaymentEvent;
import io.github.dilsh0d.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import uz.kassa.microservice.saga.annotation.*;
import uz.kassa.microservice.saga.event.SagaExceptionHandler;
import uz.kassa.microservice.saga.gateway.SagaGateway;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Tadjiev Dilshod
 */
@SagaOrchestration
public class OrderSaga {

    private String orderId;
    private String paymentId;
    private PaymentType paymentType;
    private BigDecimal amount = new BigDecimal("20.5");
    private List<Integer> itemsId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Integer> getItemsId() {
        return itemsId;
    }

    public void setItemsId(List<Integer> itemsId) {
        this.itemsId = itemsId;
    }

    @Autowired
    private transient SagaGateway sagaGateway;

    @Autowired
    private transient OrderPushNotification orderPushNotification;

    @Autowired
    private transient OrderService orderService;

    @SagaOrchestStart
    @SagaOrchestEventHandler
    public void handler(CreateOrderEvent event){
        this.orderId = event.getId();
        this.itemsId = event.getItems();

        orderService.createOrder(event, amount);
        orderPushNotification.sentClientNotification(event.getId(), "YOUR ORDER CREATED, PLEASE SELECT PAYMENT TYPE AND PAY FROM IT : [ " + PaymentType.getStrings()+" ]");
    }

    @SagaOrchestEventHandler
    public void handler(ChooseOrderPaymentTypeEvent event) {
        if (paymentId == null) {
            CreateReceiptPaymentEvent createReceiptPaymentEvent = new CreateReceiptPaymentEvent();
            createReceiptPaymentEvent.setId(UUID.randomUUID().toString());
            createReceiptPaymentEvent.setOrderId(orderId);
            createReceiptPaymentEvent.setPaymentType(event.getPaymentType());
            createReceiptPaymentEvent.setAmount(amount);
            sagaGateway.send(createReceiptPaymentEvent);

        } else {
            TryAgainReceiptPaymentEvent againReceiptPaymentEvent = new TryAgainReceiptPaymentEvent();
            againReceiptPaymentEvent.setId(paymentId);
            againReceiptPaymentEvent.setPaymentType(event.getPaymentType());
            sagaGateway.send(againReceiptPaymentEvent);
        }
    }

    @SagaOrchestEventHandler
    public void handler(OrderPaymentEntityCreatedEvent event) {
        this.paymentId = event.getPaymentId();
        this.paymentType = event.getPaymentType();

        orderService.updateOrder(event);
        orderPushNotification.sentClientNotification(event.getId(), "PAYMENT ID:[ " + paymentId + " ] " + paymentType.name() + " SUCCESSFUL SELECTED. YOU CAN PAY FROM IT'S PAYMENT TYPE");
    }

    @SagaOrchestEnd
    @SagaOrchestEventHandler
    public void handler(SuccessOrderEvent event) {
        orderService.orderSuccessfulPaymentDone(event);
        orderPushNotification.sentClientNotification(event.getId(), "ORDER SUCCESSFUL PAYMENT DONE.");
    }

    @SagaOrchestEnd
    @SagaOrchestEventHandler
    public void handler(RollbackOrderEvent event) {
        orderService.orderProcessFail(event);

        if(!event.isCallPaymentSaga()) {
            RollbackPaymentEvent rollbackPaymentEvent = new RollbackPaymentEvent();
            rollbackPaymentEvent.setId(paymentId);
            rollbackPaymentEvent.setCallOrderSaga(true);
            sagaGateway.send(rollbackPaymentEvent);

        }
        orderPushNotification.sentClientNotification(event.getId(), "ORDER PROCESS FAIL AND ROLLBACK");
    }


    @SagaOrchestException
    public void exceptionHandler(SagaExceptionHandler sagaExceptionHandler){
        orderPushNotification.sentClientNotification(sagaExceptionHandler.getSagaId(),
                "ORDER EXCEPTION IN EVENT CLASS"+sagaExceptionHandler.getExceptionEventClass()
                        +" SAGA METHOD NAME "+ sagaExceptionHandler.getExceptionSagaMethodName() +"EXCEPTION MESSAGE :["+sagaExceptionHandler.getException().getMessage()+"]");

        RollbackOrderEvent rollbackOrderEvent = new RollbackOrderEvent();
        rollbackOrderEvent.setId(orderId);
        sagaGateway.send(rollbackOrderEvent);
    }
}
