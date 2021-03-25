package io.github.dilsh0d.model.entity;

import io.github.dilsh0d.enums.PaymentType;
import io.github.dilsh0d.model.enums.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Tadjiev Dilshod
 */
@Data
@Entity
@Table(name = "tbl_payment")
public class PaymentEntity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "paymentType")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "statuse")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "orderId")
    private String orderId;
}
