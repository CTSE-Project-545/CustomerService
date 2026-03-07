package com.example.demo.repository;

import com.example.demo.model.LoyaltyPointRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyPointRecordRepository extends JpaRepository<LoyaltyPointRecord, Long> {

    List<LoyaltyPointRecord> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
