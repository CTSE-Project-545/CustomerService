package com.example.demo.client;

import com.example.demo.dto.PaymentOrderDetailResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Feign client that calls PAYMENT-SERVICE via Eureka using its service name.
 */
@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @GetMapping("/api/payments/paymentDetails")
    List<PaymentOrderDetailResponseDTO> getPaymentDetailsWithOrderAndProduct();
}
