package com.example.demo.dto;

/**
 * Wrapper DTO that combines customer, payment, order and product details.
 */
public class CustomerPaymentOrderDetailResponseDTO {

	private CustomerSummaryDTO customer;
	private PaymentSummaryDTO payment;
	private OrderDetailResponseDTO order;

	public CustomerSummaryDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSummaryDTO customer) {
		this.customer = customer;
	}

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
