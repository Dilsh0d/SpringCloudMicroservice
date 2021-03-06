package io.github.dilsh0d.enums;

/**
 * @author Tadjiev Dilshod
 */
public enum PaymentType {
    Stripe, PayPal, None;

    public static String getStrings(){
        String str = "";
        for(PaymentType type:values()) {
            if(PaymentType.None.equals(type))
                continue;
            if (!str.equals("")) {
                str += ", ";
            }
            str += type.name();
        }
        return str;
    }
}
