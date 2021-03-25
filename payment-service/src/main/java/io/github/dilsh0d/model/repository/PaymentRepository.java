package io.github.dilsh0d.model.repository;

import io.github.dilsh0d.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tadjiev Dilshod
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity,String> {
}
