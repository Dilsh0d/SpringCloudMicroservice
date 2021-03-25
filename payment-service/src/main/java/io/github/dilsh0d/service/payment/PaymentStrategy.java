package io.github.dilsh0d.service.payment;

import java.math.BigDecimal;

/**
 * @author Tadjiev Dilshod
 */
public interface PaymentStrategy {

    void pay(BigDecimal amount);
}
