package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
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

    // ⭐ Integration endpoint called by payment-service
    @PostMapping("/{id}/loyalty")
    public Customer updateLoyalty(
            @PathVariable Long id,
            @RequestBody LoyaltyRequest request
    ) {
        return service.addLoyaltyPoints(id, request.getAmountPaid(), request.getPaymentId());
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

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
}