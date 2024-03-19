package com.llama.api.billings.repository;

import com.llama.api.billings.models.Billings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billings, UUID> {
}
