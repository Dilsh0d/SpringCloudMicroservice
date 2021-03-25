package io.github.dilsh0d.model.repository;

import io.github.dilsh0d.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tadjiev Dilshod
 */
public interface OrderRepository extends JpaRepository<OrderEntity,String> {
}
