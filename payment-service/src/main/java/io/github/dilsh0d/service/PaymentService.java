package io.github.dilsh0d.service;

import io.github.dilsh0d.model.entity.PaymentEntity;
import io.github.dilsh0d.model.enums.PaymentStatus;
import io.github.dilsh0d.model.repository.PaymentRepository;
import io.github.dilsh0d.payment.events.CardPayPaymentEvent;
import io.github.dilsh0d.payment.events.CreateReceiptPaymentEvent;
import io.github.dilsh0d.service.payment.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Tadjiev Dilshod
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void createPayment(CreateReceiptPaymentEvent event) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setUuid(event.getId());
        paymentEntity.setCreateDate(LocalDateTime.now());
        paymentEntity.setAmount(event.getAmount());
        paymentEntity.setOrderId(event.getOrderId());
        paymentEntity.setPaymentType(event.getPaymentType());
        paymentEntity.setStatus(PaymentStatus.CreateReceipt);

        paymentRepository.save(paymentEntity);
    }

    public void pay(CardPayPaymentEvent event, PaymentStrategy paymentStrategy) {
        Optional<PaymentEntity> paymentEntityOptional = paymentRepository.findById(event.getId());
        if(paymentEntityOptional.isPresent()){
            PaymentEntity paymentEntity = paymentEntityOptional.get();
            paymentEntity.setStatus(PaymentStatus.Done);

            paymentStrategy.pay(paymentEntity.getAmount());

            paymentRepository.save(paymentEntity);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
