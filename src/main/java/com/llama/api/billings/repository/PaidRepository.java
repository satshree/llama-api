package com.llama.api.billings.repository;

import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Paid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaidRepository extends JpaRepository<Paid, UUID> {
    List<Paid> findByBill(Billings bill);
}
