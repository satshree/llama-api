package com.llama.api.billings.repository;

import com.llama.api.billings.models.BillingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillingInfoRepository extends JpaRepository<BillingInfo, UUID> {
    public Optional<List<BillingInfo>> findByEmail(String email);
}
