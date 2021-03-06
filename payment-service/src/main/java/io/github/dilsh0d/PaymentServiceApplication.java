package io.github.dilsh0d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import uz.kassa.microservice.saga.annotation.EnableSagaOrchestration;

@EnableSagaOrchestration
@SpringBootApplication
@EnableEurekaClient
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
