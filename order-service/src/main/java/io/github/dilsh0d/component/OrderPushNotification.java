package io.github.dilsh0d.component;

import org.springframework.stereotype.Component;

/**
 * @author Tadjiev Dilshod
 */
@Component
public class OrderPushNotification {

    /**
     * Web Socket or Google firebase
     * @param message
     */
    public void sentClientNotification(String orderId, String message) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("ORDER ID = " + orderId);
        System.out.println(message);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
