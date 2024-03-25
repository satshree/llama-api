package com.llama.api.billings.repository;

import com.llama.api.billings.models.BillingInfo;
import com.llama.api.billings.models.Billings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billings, UUID> {
//    public List<Billings> findByUser(Users user);

    public Billings findByBillingInfo(BillingInfo billingInfo);
}
