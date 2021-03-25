package io.github.dilsh0d.service;

import io.github.dilsh0d.order.events.CreateOrderEvent;
import io.github.dilsh0d.model.entity.OrderEntity;
import io.github.dilsh0d.model.enums.OrderStatus;
import io.github.dilsh0d.enums.PaymentType;
import io.github.dilsh0d.model.repository.OrderRepository;
import io.github.dilsh0d.order.events.OrderPaymentEntityCreatedEvent;
import io.github.dilsh0d.order.events.SuccessOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Tadjiev Dilshod
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(CreateOrderEvent event, BigDecimal amount) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(event.getId());
        orderEntity.setItems(event.getItems().toString());
        orderEntity.setAmount(amount);
        orderEntity.setCreateDate(LocalDateTime.now());
        orderEntity.setPaymentType(PaymentType.None);
        orderEntity.setStatus(OrderStatus.Create);

        orderRepository.save(orderEntity);
    }

    public void updateOrder(OrderPaymentEntityCreatedEvent event) {
        Optional<OrderEntity> orderEntityOptional= orderRepository.findById(event.getId());
        if(orderEntityOptional.isPresent()){
            OrderEntity orderEntity = orderEntityOptional.get();
            orderEntity.setStatus(OrderStatus.InProgress);
            orderEntity.setPaymentId(event.getPaymentId());
            orderEntity.setPaymentType(event.getPaymentType());
            orderRepository.save(orderEntity);
        }
    }

    public void orderSuccessfulPaymentDone(SuccessOrderEvent event) {
        Optional<OrderEntity> orderEntityOptional= orderRepository.findById(event.getId());
        if(orderEntityOptional.isPresent()){
            OrderEntity orderEntity = orderEntityOptional.get();
            orderEntity.setStatus(OrderStatus.Done);
            orderRepository.save(orderEntity);
        }
    }
}
