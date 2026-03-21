package com.example.demo.controller;

import com.example.demo.dto.CustomerPaymentOrderDetailResponseDTO;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // Create customer
    @PostMapping
    public Customer create(@RequestBody Customer c) {
        return service.createCustomer(c);
    }

    @GetMapping
    public List<Customer> getAll() {
        return service.getAllCustomers();
    }

    // Get customer
    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    // Update customer name and email
    @PutMapping("/{id}")
    public Customer update(
            @PathVariable Long id,
            @RequestBody UpdateCustomerRequest request
    ) {
        return service.updateCustomer(id, request.getName(), request.getEmail());
    }

    

    // New endpoint: payment -> order -> product details via PAYMENT-SERVICE (Eureka)
    @GetMapping("/payment-order-product-details")
    public List<CustomerPaymentOrderDetailResponseDTO> getPaymentOrderProductDetails() {
        return service.getPaymentOrderProductDetails();
    }
}

// Request payload for updating name/email
class UpdateCustomerRequest {
    private String name;
    private String email;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

// Request payload class (from payment-service)
class LoyaltyRequest {
    private double amountPaid;
    /** Payment ID from payment-service for cross-reference. */
    private String paymentId;
    /** Customer mobile number used for grouping customers. */
    private String mobile;

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
}