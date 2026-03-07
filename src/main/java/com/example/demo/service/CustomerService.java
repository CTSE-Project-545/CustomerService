package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.LoyaltyPointRecord;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LoyaltyPointRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final double LOYALTY_RATE = 0.05;

    private final CustomerRepository repo;
    private final LoyaltyPointRecordRepository loyaltyRecordRepo;

    public CustomerService(CustomerRepository repo, LoyaltyPointRecordRepository loyaltyRecordRepo) {
        this.repo = repo;
        this.loyaltyRecordRepo = loyaltyRecordRepo;
    }

    public Customer createCustomer(Customer c) {
        return repo.save(c);
    }

    public Customer getCustomer(Long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Update customer name and/or email. Returns null if customer not found.
     */
    public Customer updateCustomer(Long id, String name, String email) {
        Customer customer = getCustomer(id);
        if (customer == null) return null;
        if (name != null) customer.setName(name);
        if (email != null) customer.setEmail(email);
        return repo.save(customer);
    }

    /**
     * Add loyalty points from a payment (called by payment-service).
     * Stores paymentId on customer, updates balance, and persists a loyalty record.
     */
    public Customer addLoyaltyPoints(Long id, double amountPaid, String paymentId) {
        Customer customer = getCustomer(id);
        if (customer == null) return null;

        double points = amountPaid * LOYALTY_RATE;
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);

        if (paymentId != null && !paymentId.isBlank()) {
            if (!customer.getPaymentIds().contains(paymentId)) {
                customer.getPaymentIds().add(paymentId);
            }
        }

        customer = repo.save(customer);

        LoyaltyPointRecord record = new LoyaltyPointRecord();
        record.setCustomer(customer);
        record.setPointsAdded(points);
        record.setPaymentId(paymentId);
        loyaltyRecordRepo.save(record);

        return customer;
    }
}