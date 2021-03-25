package io.github.dilsh0d.component;

import org.springframework.stereotype.Component;

/**
 * @author Tadjiev Dilshod
 */
@Component
public class PaymentPushNotification {
    public void sendMessageClient(String paymentId, String orderId, String message) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("PAYMENT ID = " + paymentId);
        System.out.println("ORDER ID = " + orderId);
        System.out.println(message);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
