package com.example.demo.dto;

/**
 * Mirrors the structure of com.ctse.payment_service.dto.PaymentOrderDetailResponseDTO
 * from PAYMENT-SERVICE so JSON can be deserialized correctly.
 */
public class PaymentOrderDetailResponseDTO {

    private PaymentSummaryDTO payment;
    private OrderDetailResponseDTO order;

    public PaymentSummaryDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentSummaryDTO payment) {
        this.payment = payment;
    }

    public OrderDetailResponseDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDetailResponseDTO order) {
        this.order = order;
    }
}
