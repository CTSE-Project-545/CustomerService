package com.example.demo.model;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Stores each loyalty point grant (from payment-service) for history and audit.
 */
@Entity
@Table(name = "loyalty_point_record")
public class LoyaltyPointRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /** Points added in this grant (e.g. 5% of amountPaid). */
    private double pointsAdded;

    /** Payment ID from payment-service that triggered this grant. */
    private String paymentId;

    private Instant createdAt = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getPointsAdded() {
        return pointsAdded;
    }

    public void setPointsAdded(double pointsAdded) {
        this.pointsAdded = pointsAdded;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
