package com.microservice.ePharmaMS.order_service.client;

import com.microservice.ePharmaMS.order_service.DTO.PaymentRequest;
import com.microservice.ePharmaMS.order_service.DTO.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8080")
public interface PaymentClient {
    @PostMapping("/api/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}
