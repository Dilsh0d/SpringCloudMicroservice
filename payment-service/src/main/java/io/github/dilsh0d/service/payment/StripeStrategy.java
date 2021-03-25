package io.github.dilsh0d.service.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
@AllArgsConstructor
public class StripeStrategy implements PaymentStrategy {

    private String cardNumber;
    private String cardExpired;
    private String cvv;

    @Override
    public void pay(BigDecimal amount) {
        throw new RuntimeException(amount + " PAID USING STRIPE exception .");
    }
}
