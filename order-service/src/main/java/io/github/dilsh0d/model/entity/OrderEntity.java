package io.github.dilsh0d.model.entity;

import io.github.dilsh0d.model.enums.OrderStatus;
import io.github.dilsh0d.enums.PaymentType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Tadjiev Dilshod
 */
@Data
@Entity
@Table(name = "tbl_orders")
public class OrderEntity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "paymentType")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "paymentId")
    private String paymentId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "items")
    private String items;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
