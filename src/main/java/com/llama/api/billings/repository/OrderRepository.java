package com.llama.api.billings.repository;

import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    public List<Orders> findByBill(Billings bill);
}
