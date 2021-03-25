package io.github.dilsh0d.service.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Tadjiev Dilshod
 */
@Getter
@Setter
@AllArgsConstructor
public class PayPalStrategy  implements PaymentStrategy{

    private String cardNumber;
    private String cardExpired;
    private String cvv;

    @Override
    public void pay(BigDecimal amount) {
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(amount + " PAID USING PAYPAL.");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
        System.out.println(" ++++++++++++++++++++++++++++ ");
    }
}
