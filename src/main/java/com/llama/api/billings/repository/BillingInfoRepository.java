package com.llama.api.billings.repository;

import com.llama.api.billings.models.BillingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, UUID> {
}
