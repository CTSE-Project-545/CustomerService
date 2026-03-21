package com.example.demo.service;

import com.example.demo.client.PaymentClient;
import com.example.demo.dto.CustomerPaymentOrderDetailResponseDTO;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.dto.PaymentOrderDetailResponseDTO;
import com.example.demo.dto.PaymentSummaryDTO;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LoyaltyPointRecordRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repo;
    private final PaymentClient paymentClient;

    public CustomerService(CustomerRepository repo,
                 LoyaltyPointRecordRepository loyaltyRecordRepo,
                 PaymentClient paymentClient) {
        this.repo = repo;
        this.paymentClient = paymentClient;
    }

    public Customer createCustomer(Customer c) {
        if (c.getMobile() != null && !c.getMobile().isBlank()) {
            Customer existing = repo.findByMobile(c.getMobile()).orElse(null);
            if (existing != null) {
                // Optionally refresh basic details
                if (c.getName() != null && !c.getName().isBlank()) {
                    existing.setName(c.getName());
                }
                if (c.getEmail() != null && !c.getEmail().isBlank()) {
                    existing.setEmail(c.getEmail());
                }

                // Add loyalty points (plus)
                existing.setLoyaltyPoints(
                        existing.getLoyaltyPoints() + c.getLoyaltyPoints());

                // Merge payment IDs, avoiding duplicates
                if (c.getPaymentIds() != null) {
                    for (String pid : c.getPaymentIds()) {
                        if (pid != null && !pid.isBlank()
                                && !existing.getPaymentIds().contains(pid)) {
                            existing.getPaymentIds().add(pid);
                        }
                    }
                }

                return repo.save(existing);
            }
        }
        return repo.save(c);
    }

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public Customer getCustomer(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Customer updateCustomer(Long id, String name, String email) {
        Customer customer = getCustomer(id);
        if (customer == null)
            return null;
        if (name != null)
            customer.setName(name);
        if (email != null)
            customer.setEmail(email);
        return repo.save(customer);
    }

    /**
     * Calls PAYMENT-SERVICE (via Eureka service name PAYMENT-SERVICE) to
     * retrieve payment -> order -> product details and wraps them with
     * matching customer details (based on stored paymentIds).
     */
    public List<CustomerPaymentOrderDetailResponseDTO> getPaymentOrderProductDetails() {
        List<PaymentOrderDetailResponseDTO> paymentOrderDetails =
                paymentClient.getPaymentDetailsWithOrderAndProduct();

        // Build an in-memory map paymentId -> Customer for quick lookup
        List<Customer> customers = repo.findAll();
        Map<String, Customer> customerByPaymentId = new HashMap<>();
        for (Customer customer : customers) {
            if (customer.getPaymentIds() == null) {
                continue;
            }
            for (String pid : customer.getPaymentIds()) {
                if (pid != null && !pid.isBlank() && !customerByPaymentId.containsKey(pid)) {
                    customerByPaymentId.put(pid, customer);
                }
            }
        }

        List<CustomerPaymentOrderDetailResponseDTO> result = new ArrayList<>();
        for (PaymentOrderDetailResponseDTO detail : paymentOrderDetails) {
            PaymentSummaryDTO payment = detail.getPayment();
            Customer matchedCustomer = null;
            if (payment != null && payment.getPaymentId() != null) {
                matchedCustomer = customerByPaymentId.get(payment.getPaymentId());
            }

            CustomerPaymentOrderDetailResponseDTO wrapper =
                    new CustomerPaymentOrderDetailResponseDTO();
            if (matchedCustomer != null) {
                CustomerSummaryDTO customerDto = new CustomerSummaryDTO();
                customerDto.setId(matchedCustomer.getId());
                customerDto.setName(matchedCustomer.getName());
                customerDto.setEmail(matchedCustomer.getEmail());
                customerDto.setMobile(matchedCustomer.getMobile());
                customerDto.setLoyaltyPoints(matchedCustomer.getLoyaltyPoints());
                wrapper.setCustomer(customerDto);
            }

            wrapper.setPayment(payment);
            wrapper.setOrder(detail.getOrder());
            result.add(wrapper);
        }

        return result;
    }

}